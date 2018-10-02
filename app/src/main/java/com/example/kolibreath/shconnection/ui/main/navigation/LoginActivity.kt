package com.example.kolibreath.shconnection.ui.main.navigation

import USER_PARENT
import USER_TEACHER
import USER_TYPE
import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.ui.ToolbarActivity
import com.example.kolibreath.shconnection.extensions.text

class LoginActivity:ToolbarActivity(){

  private var mUserType :Int = USER_TEACHER

  private lateinit var mEdtInputNumber: EditText
  private lateinit var mBtnCreateClass :TextView
  private lateinit var mBtnConfirm :Button

  //todo 添加家长和老师请求数据库的api
  private val mTeacherApi = View.OnClickListener { }
  private val mParentApi = View.OnClickListener {  }


  companion object {
    fun start(context : Context, userType: Int){
      context.startActivity(Intent(context,LoginActivity::class.java).apply {
        this.putExtra(USER_TYPE,userType)
      })
    }
  }

  private fun initView(){
    mEdtInputNumber = findViewById(R.id.edt_number)
    mBtnCreateClass = findViewById(R.id.btn_create_class)
    mBtnConfirm     = findViewById(R.id.btn_confirm)
  }

  private fun updateView(userType:Int){
    when(userType){
      USER_PARENT ->{
        mEdtInputNumber.hint = text(R.string.input_parent_number)
        mBtnCreateClass.visibility = View.GONE
      }
      USER_TEACHER ->{
        mEdtInputNumber.hint = text(R.string.input_teacher_number)
        mBtnCreateClass.setOnClickListener {
          //todo goto 创建班级
        }
      }
    }
  }

   override fun onCreate(
    savedInstanceState: Bundle?
  ) {
    super.onCreate(savedInstanceState)
     setContentView(R.layout.activity_login)

     mUserType = intent.getIntExtra(USER_TYPE,-1)
     initView()
     updateView(userType =  mUserType)
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

