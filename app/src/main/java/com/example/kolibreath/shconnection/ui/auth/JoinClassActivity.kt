package com.example.kolibreath.shconnection.ui.auth

import USER_NONE
import USER_PARENT
import USER_TEACHER
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.R.id.action_scan_from_actual_view
import com.example.kolibreath.shconnection.R.id.action_scan_from_system_gallery
import com.example.kolibreath.shconnection.base.ParentInit
import com.example.kolibreath.shconnection.base.TeacherInit
import com.example.kolibreath.shconnection.base.net.NetFactory
import com.example.kolibreath.shconnection.base.ui.ToolbarActivity
import com.example.kolibreath.shconnection.extensions.decode
import com.example.kolibreath.shconnection.extensions.showSnackBarShort
import com.uuzuche.lib_zxing.activity.CaptureActivity
import com.uuzuche.lib_zxing.activity.CodeUtils
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

class JoinClassActivity:ToolbarActivity() {


  private val REQUEST_IMAGE = 0xFF
  private val REQUEST_SCAN=  0xFA

  private lateinit var mEdtNumber:EditText
  private lateinit var mEdtPassword:EditText
  private lateinit var mEdtName:EditText
  private lateinit var mEdtSubject:EditText

  private lateinit var mBtnConfirm:Button

  private lateinit var mCbStudent:CheckBox
  private lateinit var mCbTeacher:CheckBox

  private var mUserType:Int = USER_TEACHER

  private lateinit var mClassId :String

  companion object {
    fun start(context:Context){
      context.startActivity(Intent(context,JoinClassActivity::class.java))
    }
  }

  private fun checkUserType():Int =
    if(mCbStudent.isChecked && !mCbTeacher.isChecked){
      USER_PARENT
    }else if(!mCbStudent.isChecked && mCbTeacher.isChecked){
      USER_TEACHER
    }else if(mCbStudent.isChecked && mCbTeacher.isChecked){
      USER_NONE
    }else{ USER_NONE
    }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when(item.itemId){
       action_scan_from_actual_view  ->{
        val intent = Intent(this@JoinClassActivity,CaptureActivity::class.java)
        startActivityForResult(intent,REQUEST_SCAN)
      }
      action_scan_from_system_gallery ->{
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(intent,REQUEST_IMAGE)
      }
    }
    return super.onOptionsItemSelected(item)
  }

  @RequiresApi(VERSION_CODES.O)
  override fun onActivityResult(
    requestCode: Int,
    resultCode: Int,
    data: Intent?
  ) {
    when(requestCode){
      REQUEST_SCAN ->{
        val bundle = data?.extras ?: return

        if(bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS){
          val result = bundle.getString(CodeUtils.RESULT_STRING )
          mClassId =decode(result)
        }
      }

      REQUEST_IMAGE ->{
        if(data == null)
          return

         val uri = data.data
         val inputStream= contentResolver.openInputStream(uri)

        val file = File.createTempFile("temp",".jpg")
        Files.copy(inputStream,file.toPath(),StandardCopyOption.REPLACE_EXISTING)

          CodeUtils.analyzeBitmap(file.path,object:CodeUtils.AnalyzeCallback{
            override fun onAnalyzeSuccess(
              mBitmap: Bitmap?,
              result: String?
            ) {
              mClassId = decode(result!!)
            }

            override fun onAnalyzeFailed() {
              showSnackBarShort("解析失败")
            }
          })
      }
    }
    super.onActivityResult(requestCode, resultCode, data)
  }
  private fun initView(){
    mEdtName = findViewById(R.id.edt_name)
    mEdtNumber = findViewById(R.id.edt_number)
    mEdtPassword = findViewById(R.id.edt_password)
    mEdtSubject = findViewById(R.id.edt_subject)

    mBtnConfirm = findViewById(R.id.btn_confirm)

    mCbStudent = findViewById(R.id.cb_student)
    mCbTeacher = findViewById(R.id.cb_teacher)

    mCbTeacher.isChecked = true

    mCbStudent.setOnClickListener{
      mEdtSubject.visibility = View.INVISIBLE
      mCbTeacher.isChecked = false
    }

    mCbTeacher.setOnClickListener{
      mEdtSubject.visibility = View.VISIBLE
      mCbStudent.isChecked = false
    }

    mBtnConfirm.setOnClickListener{
      val number = mEdtNumber.editableText.toString()
      val password = mEdtPassword.editableText.toString()
      val name = mEdtName.editableText.toString()
      val subject = mEdtSubject.editableText.toString()

      //如果是家长登录 允许subject为空
      if(number.isEmpty || password.isEmpty || name.isEmpty)
        return@setOnClickListener

      //todo 上传用户的信息
      mUserType = checkUserType()
      when ( mUserType){
        USER_TEACHER -> {
          //这种情况下的老师是没有工号的 先加入一个班级
          //如果有工号地老师回去直接登录
          val teacherInit = TeacherInit(wid =
          number,password = password,name = name,subject = subject)

          NetFactory.retrofitService
              .teacherInitJoinClass(classId = mClassId,teacherInit = teacherInit)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(object : Subscriber<Any>() {
                override fun onNext(t: Any?) {
                  //老师注册完成之后需要再去登录一波
                  LoginActivity.start(this@JoinClassActivity)
                }
                override fun onCompleted() {finish()}
                override fun onError(e: Throwable?) {e!!.printStackTrace()}
              })
        }
        USER_PARENT -> {
          //todo parent register api
          val parentInit = ParentInit(sid = number,
              password = password)

          NetFactory.retrofitService
              .parentInitJoinClass(classId = mClassId,parentInit = parentInit)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(object : Subscriber<Any>() {
                override fun onNext(t: Any?) {
                  //家长注册完成之后需要再去登录一波
                  LoginActivity.start(this@JoinClassActivity)
                }
                override fun onCompleted() {finish()}
                override fun onError(e: Throwable?) {e!!.printStackTrace()}
              })
        }
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_join_class)

    initView()
  }

}


val String.isEmpty:Boolean
get() = TextUtils.isEmpty(this)