package com.example.kolibreath.shconnection.ui.auth

import USER_NONE
import USER_PARENT
import USER_TEACHER
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.ui.ToolbarActivity
import com.example.kolibreath.shconnection.extensions.showErrorSnackbarShort

class LoginActivity:ToolbarActivity(){

  private var mUserType :Int = USER_NONE

  private lateinit var mEdtInputNumber: EditText
  private lateinit var mEdtInputPassword: EditText
  private lateinit var mBtnConfirm :Button

  private lateinit var mCbTeacher:CheckBox
  private lateinit var mCbStudent:CheckBox

  //todo 添加家长和老师请求数据库的api
  //家长和老师的api的返回值
  //老师会保存班级信息 老师交了那几个班级
  //家长会保存现在是那个班级 curClassId
  private val mTeacherApi = View.OnClickListener { }
  private val mParentApi = View.OnClickListener {  }


  companion object {
    fun start(context : Context){
      context.startActivity(Intent(context,
          LoginActivity::class.java))
    }
  }

  private fun initView(){

    enableBack(boolean =  false)

    mEdtInputNumber = findViewById(R.id.edt_number)
    mEdtInputPassword = findViewById(R.id.edt_password)
    mBtnConfirm     = findViewById(R.id.btn_confirm)

    mCbStudent = findViewById(R.id.cb_student)
    mCbTeacher = findViewById(R.id.cb_teacher)


    mBtnConfirm.setOnClickListener{
      if(TextUtils.isEmpty(mEdtInputNumber.editableText.toString())||
          TextUtils.isEmpty(mEdtInputPassword.editableText.toString()))
        return@setOnClickListener

      mUserType = checkUserType()
      when(mUserType){
        USER_TEACHER -> {
          //todo Teacher login api
        }
        USER_PARENT ->{
          // todo Parent login api
        }else -> {
        showErrorSnackbarShort("需要选择一个用户类型")
      }
      }
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


   override fun onCreate(
    savedInstanceState: Bundle?
  ) {
    super.onCreate(savedInstanceState)
     setContentView(R.layout.activity_login)

     initView()
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when(item.itemId){
      android.R.id.home ->{
        this.finish()
      }

    }
    return super.onOptionsItemSelected(item)
  }

}

