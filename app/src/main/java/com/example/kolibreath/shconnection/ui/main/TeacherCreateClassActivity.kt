package com.example.kolibreath.shconnection.ui.main

import CLASS_ID
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
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
import rx.Scheduler
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

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

  //todo 需要在弄清楚child和 teacher的数据格式之后进行读取的测试
  private val mTeacherCreateClassApi: () ->Unit = label@{
    val name = mEdtTeacherName.editableText.toString()
    val wid = mEdtTeacherNumber.editableText.toString()
    val className = mEdtClassName.editableText.toString()

    if(name.isEmpty || wid.isEmpty || className.isEmpty)
      return@label

    //解析老师的xls
    val xlsUtils = XlsUtils(mPath)
    val teachers = xlsUtils.getTeachers()
    val children = xlsUtils.getChildren()

    val teacherCreateClassBody = TeacherCreateClassBody(
        wid = wid,class_name = className,teachers_list = teachers,children_list = children
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

          override fun onCompleted() {
            val encrypted=  encrypt(mClassId)
            val bitmap = createQRcode(encrypted = encrypted)
          }

          override fun onError(e: Throwable?) {e!!.printStackTrace()}
        })

  }
  //todo 在创建完成的回调之后生成二维码
  //创建一个加密过了的二维码图片
  private fun createQRcode(encrypted:String):Bitmap? = QRCodeUtil.createQRCodeBitmap(encrypted,480,480)

  private fun createClass(){
    mEdtTeacherName = findViewById(R.id.edt_teacher_name)
    mEdtTeacherNumber = findViewById(R.id.edt_teacher_number)
    mEdtClassName = findViewById(R.id.edt_class_name)

    mBtnImportStudent = findViewById(R.id.btn_import_students_info)
    mBtnImportTeacher = findViewById(R.id.btn_import_teacher_info)

    mBtnImportTeacher.setOnClickListener{
      //todo 读取教师的信息
    }
    mBtnImportStudent.setOnClickListener{
      //todo 读取学生的信息
    }
  }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_teacher_create_class)

    createClass()
  }
}