package com.example.kolibreath.shconnection.ui.main

import CLASS_ID
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.Environment
import android.support.annotation.RequiresApi
import android.widget.Button
import android.widget.EditText
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.CreatedClassId
import com.example.kolibreath.shconnection.base.TeacherCreateClassBody
import com.example.kolibreath.shconnection.base.net.NetFactory
import com.example.kolibreath.shconnection.base.ui.ToolbarActivity
import com.example.kolibreath.shconnection.extensions.Preference
import com.example.kolibreath.shconnection.extensions.QRCodeUtil
import com.example.kolibreath.shconnection.extensions.XlsUtils
import com.example.kolibreath.shconnection.extensions.encrypt
import com.example.kolibreath.shconnection.extensions.showSnackBarShort
import com.example.kolibreath.shconnection.ui.auth.LoginActivity
import com.example.kolibreath.shconnection.ui.auth.isEmpty
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.util.LinkedList

class TeacherCreateClassActivity :ToolbarActivity() {

  private lateinit var mEdtTeacherName:EditText
  private lateinit var mEdtTeacherNumber:EditText
  private lateinit var mEdtClassName:EditText
  private lateinit var mBtnImportStudent:Button
  private lateinit var mBtnImportTeacher:Button

  private  var mClassId:String by Preference<String>(CLASS_ID,"")
  companion object{
    fun start(context:Context){
      context.startActivity(Intent(context,this::class.java))
    }
  }

  //todo get path of xlxs
  private lateinit var mPath:String

  private lateinit var mTeachers :LinkedList<TeacherCreateClassBody.TeachersListBean>
  private lateinit var mChildren :LinkedList<TeacherCreateClassBody.ChildrenListBean>

  private val mTeacherCreateClassApi: () ->Unit = label@{
    val name = mEdtTeacherName.editableText.toString()
    val wid = mEdtTeacherNumber.editableText.toString()
    val className = mEdtClassName.editableText.toString()

    if(name.isEmpty || wid.isEmpty || className.isEmpty)
      return@label


    val teacherCreateClassBody = TeacherCreateClassBody(
        wid = wid,class_name = className,
        teachers_list = mTeachers,children_list = mChildren
    )

    NetFactory.retrofitService
        .teacherCreateClass(teacherCreateClassBody)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object:Subscriber<CreatedClassId>(){
          override fun onNext(t: CreatedClassId?) {
            mClassId= t!!.class_id.toString()
            showSnackBarShort("班级创建成功")
            LoginActivity.start(this@TeacherCreateClassActivity)
            finish()
          }

          @RequiresApi(VERSION_CODES.O)
          override fun onCompleted() {
            val encrypted=  encrypt(mClassId)
            val bitmap = createQRcode(encrypted = encrypted)
            bitmap!!.saveBitmap2Local()
          }

          override fun onError(e: Throwable?) {e!!.printStackTrace()}
        })

  }
  //创建一个加密过了的二维码图片
  private fun createQRcode(encrypted:String):Bitmap? = QRCodeUtil.createQRCodeBitmap(encrypted,480,480)

  private val name:String get() =  "SHConnection${System.currentTimeMillis()}"

  private fun Bitmap.saveBitmap2Local(){
    val file = File("${Environment.getExternalStorageDirectory().path}/${name}.jpg" )
    if(file.exists())
      file.delete()
    val out = FileOutputStream(file)
    if(this.compress(Bitmap.CompressFormat.PNG,90,out)){
      out.flush()
      out.close()

      val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
      val uri = Uri.fromFile(file)
      intent.data = uri
      this@TeacherCreateClassActivity.sendBroadcast(intent)
      showSnackBarShort("保存成功")
    }
  }

  private fun createClass(){
    mEdtTeacherName = findViewById(R.id.edt_teacher_name)
    mEdtTeacherNumber = findViewById(R.id.edt_teacher_number)
    mEdtClassName = findViewById(R.id.edt_class_name)

    mBtnImportStudent = findViewById(R.id.btn_import_students_info)
    mBtnImportTeacher = findViewById(R.id.btn_import_teacher_info)

      val xlsUtils = XlsUtils(mPath)
    mBtnImportTeacher.setOnClickListener{
      //解析老师的xls
       mTeachers = xlsUtils.getTeachers()
    }
    mBtnImportStudent.setOnClickListener{
       mChildren = xlsUtils.getChildren()
    }
  }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_teacher_create_class)

    createClass()
  }
}