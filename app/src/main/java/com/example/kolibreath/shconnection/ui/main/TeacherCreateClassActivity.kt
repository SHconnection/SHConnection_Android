package com.example.kolibreath.shconnection.ui.main

import CLASS_ID
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.Environment
import android.support.annotation.RequiresApi
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.CreatedClassId
import com.example.kolibreath.shconnection.base.TeacherCreateClassBody
import com.example.kolibreath.shconnection.base.net.NetFactory
import com.example.kolibreath.shconnection.base.ui.ToolbarActivity
import com.example.kolibreath.shconnection.extensions.BarcodeEncoder
import com.example.kolibreath.shconnection.extensions.XlsUtils
import com.example.kolibreath.shconnection.extensions.bgContext
import com.example.kolibreath.shconnection.extensions.encrypt
import com.example.kolibreath.shconnection.extensions.putValue
import com.example.kolibreath.shconnection.extensions.showSnackBarShort
import com.example.kolibreath.shconnection.extensions.uiContext
import com.example.kolibreath.shconnection.ui.auth.LoginActivity
import com.example.kolibreath.shconnection.ui.auth.isEmpty
import com.google.zxing.BarcodeFormat.QR_CODE
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.toast
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.util.LinkedList

class TeacherCreateClassActivity :ToolbarActivity() {
  private val IMPORT_STUDENT = 1
  private val IMPORT_TEACHER = 2

  private var mTeacherFlag = false
  private var mStudentFlag = false

  private lateinit var mUri: Uri

  private lateinit var xlsUtils: XlsUtils

  private lateinit var mEdtTeacherName:EditText
  private lateinit var mEdtTeacherNumber:EditText
  private lateinit var mEdtClassName:EditText
  private lateinit var mBtnImportStudent:Button
  private lateinit var mBtnImportTeacher:Button
  private lateinit var mBtnConfirm :Button

  private  lateinit var mClassId:String
  companion object{
    fun start(context:Context){
      context.startActivity(Intent(context,TeacherCreateClassActivity::class.java))
    }
  }

  //todo get path of xlxs
  private lateinit var mTeacherPath:String
  private lateinit var mStudentPath :String

  private lateinit var mTeachers :LinkedList<TeacherCreateClassBody.TeachersListBean>
  private lateinit var mChildren :LinkedList<TeacherCreateClassBody.ChildrenListBean>

  private val mTeacherCreateClassApi: () ->Unit = label@{
    val name = mEdtTeacherName.editableText.toString()
    val wid = mEdtTeacherNumber.editableText.toString()
    val className = mEdtClassName.editableText.toString()

    if(name.isEmpty || wid.isEmpty || className.isEmpty)
      return@label

    if(!mStudentFlag) {
      toast("学生信息导入失败")
      return@label
    }

    if(!mTeacherFlag){
      toast("老师信息导入失败")
      return@label
    }

    if(mTeachers.isEmpty() || mChildren.isEmpty())
      throw IllegalArgumentException("老师或者学生的信息为空")

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
            if (t != null) {
              mClassId = t.getClass_id().toString()

              this@TeacherCreateClassActivity.putValue(CLASS_ID,mClassId.toString())

              showSnackBarShort("班级创建成功")
            }
          }

          @RequiresApi(VERSION_CODES.O)
          override fun onCompleted() {
            val encrypted=  encrypt(mClassId)
            val bitmap = encodeAsBitmap(encrypted)
            bitmap!!.saveBitmap2Local()

            LoginActivity.start(this@TeacherCreateClassActivity)
            finish()
          }

          override fun onError(e: Throwable?) {e!!.printStackTrace()}
        })

  }
  //创建一个加密过了的二维码图片


  private fun encodeAsBitmap(str:String):Bitmap?{
    lateinit var bitmap: Bitmap
    lateinit var result : BitMatrix
    val multiFormatWriter = MultiFormatWriter()
    try{
      result = multiFormatWriter.encode(str,QR_CODE,374,374)
      bitmap = BarcodeEncoder().createBitmap(result)
    }catch (e:Exception){
      e.printStackTrace()
    }
    return bitmap
  }

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

      mBtnImportTeacher.setOnClickListener{
        //解析老师的xls
        browseDoc(type = IMPORT_TEACHER)
      }
      mBtnImportStudent.setOnClickListener{
        browseDoc(type = IMPORT_STUDENT)
      }

      mBtnConfirm =   findViewById<Button>(R.id.btn_confirm)
      mBtnConfirm.setOnClickListener{
        mTeacherCreateClassApi()
      }
    }

    override fun onActivityResult(
      requestCode: Int,
      resultCode: Int,
      data: Intent?
    ) {
      when(requestCode){
        IMPORT_STUDENT -> {
          if (resultCode == Activity.RESULT_OK) {
            mUri = data!!.data
            launch(uiContext){
              mChildren = async(bgContext){
                xlsUtils = XlsUtils(getPath(mUri)!!)
                xlsUtils.getChildren()
              }.await()
              Log.d("fuck",mChildren.toString())
              mStudentFlag = true
            }
          }
        }

        IMPORT_TEACHER ->{
          if(resultCode == Activity.RESULT_OK){
            mUri = data!!.data
            xlsUtils = XlsUtils(getPath(mUri)!!)
            mTeachers = xlsUtils.getTeachers()
            launch(uiContext){
              mTeachers = async(bgContext){
                xlsUtils = XlsUtils(getPath(mUri)!!)
                xlsUtils.getTeachers()
              }.await()
              mTeacherFlag = true
              Log.d("fuck you",mTeachers.toString())
            }
          }
        }
      }
      super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getPath(uri: Uri):String?{
      if("content" == uri.scheme.toLowerCase()){
        val projection:Array<String> = arrayOf("_data")
        val cursor = this@TeacherCreateClassActivity.contentResolver
            .query(uri,projection, null,null,null)

        val column_index = cursor.getColumnIndexOrThrow("_data")
        if(cursor.moveToFirst()){
          return cursor.getString(column_index)
        }
      }else if("file" == uri.scheme.toLowerCase()){
        return uri.path
      }
      return null
    }

    private fun browseDoc(type:Int){
      val intent = Intent(Intent.ACTION_GET_CONTENT)
      intent.type = "*/*"
      intent.addCategory(Intent.CATEGORY_OPENABLE)
      when(type){
        IMPORT_TEACHER ->{
          startActivityForResult(Intent.createChooser(intent,"选择应用打开"),IMPORT_TEACHER)
        }
        IMPORT_STUDENT -> {
          startActivityForResult(Intent.createChooser(intent,"选择应用打开"),IMPORT_STUDENT)

        }
      }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_teacher_create_class)

      createClass()
    }
  }