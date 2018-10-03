package com.example.kolibreath.shconnection.ui.auth

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
import com.example.kolibreath.shconnection.base.ui.ToolbarActivity

class JoinClassActivity:ToolbarActivity() {


  private lateinit var mEdtNumber:EditText
  private lateinit var mEdtPassword:EditText
  private lateinit var mEdtName:EditText
  private lateinit var mEdtSubject:EditText

  private lateinit var mBtnConfirm:Button

  private lateinit var mCbStudent:CheckBox
  private lateinit var mCbTeacher:CheckBox

  private var mUserType:Int = USER_NONE

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

      if(number.isEmpty || password.isEmpty || name.isEmpty || subject.isEmpty)
        return@setOnClickListener

      //todo 上传用户的信息
      mUserType = checkUserType()
      when ( mUserType){
        USER_TEACHER -> {
          //todo teacher login api
        }
        USER_PARENT -> {
          //todo parent login api
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