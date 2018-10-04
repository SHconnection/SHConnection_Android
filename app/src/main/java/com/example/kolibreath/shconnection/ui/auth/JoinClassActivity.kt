package com.example.kolibreath.shconnection.ui.auth

import SCAN_RESULT
import USER_NONE
import USER_PARENT
import USER_TEACHER
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.ParentInit
import com.example.kolibreath.shconnection.base.TeacherInit
import com.example.kolibreath.shconnection.base.net.NetFactory
import com.example.kolibreath.shconnection.base.ui.ToolbarActivity
import com.luck.picture.lib.rxbus2.Subscribe
import rx.Scheduler
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class JoinClassActivity:ToolbarActivity() {


  private lateinit var mEdtNumber:EditText
  private lateinit var mEdtPassword:EditText
  private lateinit var mEdtName:EditText
  private lateinit var mEdtSubject:EditText

  private lateinit var mBtnConfirm:Button

  private lateinit var mCbStudent:CheckBox
  private lateinit var mCbTeacher:CheckBox

  private var mUserType:Int = USER_NONE

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

  private fun initView(){
    mEdtName = findViewById(R.id.edt_name)
    mEdtNumber = findViewById(R.id.edt_number)
    mEdtPassword = findViewById(R.id.edt_password)
    mEdtSubject = findViewById(R.id.edt_subject)

    mBtnConfirm = findViewById(R.id.btn_confirm)

    mCbStudent = findViewById(R.id.cb_student)
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

    mClassId = intent.getStringExtra(SCAN_RESULT)

    initView()
  }

}


val String.isEmpty:Boolean
get() = TextUtils.isEmpty(this)