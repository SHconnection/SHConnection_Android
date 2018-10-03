package com.example.kolibreath.shconnection.ui.auth

import CLASSED_IDS
import CUR_CLASS
import LOGIN_TOKEN
import USER_NONE
import USER_PARENT
import USER_TEACHER
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.TeacherLoginBody
import com.example.kolibreath.shconnection.base.TeacherLoginToken
import com.example.kolibreath.shconnection.base.ParentLoginBody
import com.example.kolibreath.shconnection.base.ParentLoginToken
import com.example.kolibreath.shconnection.base.net.NetFactory
import com.example.kolibreath.shconnection.base.ui.ToolbarActivity
import com.example.kolibreath.shconnection.extensions.Preference
import com.example.kolibreath.shconnection.extensions.database
import com.example.kolibreath.shconnection.extensions.showErrorSnackbarShort
import org.jetbrains.anko.db.INTEGER
import org.jetbrains.anko.db.createTable
import org.jetbrains.anko.db.insert
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class LoginActivity:ToolbarActivity(){

  private var mToken :String by Preference<String>(name = LOGIN_TOKEN,default = "")

  //保存当前所在地班级id
  private var mCurrentId:Int by Preference(name = CUR_CLASS,default = -1)

  private var mUserType :Int = USER_NONE

  private lateinit var mEdtInputNumber: EditText
  private lateinit var mEdtInputPassword: EditText
  private lateinit var mBtnConfirm :Button

  private lateinit var mCbTeacher:CheckBox
  private lateinit var mCbStudent:CheckBox

  //家长和老师的api的返回值
  //老师会保存班级信息 老师交了那几个班级
  //家长会保存现在是那个班级 curClassId
  private val mTeacherApi :()->Unit= {
    val loginBody = TeacherLoginBody(
        wid = mEdtInputNumber.editableText.toString(),
        password = mEdtInputPassword.editableText.toString())

    NetFactory.
        retrofitService
        .teacherLogin(loginInfo = loginBody)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(object:Subscriber<TeacherLoginToken>(){
          override fun onNext(t: TeacherLoginToken?) {
            mToken = t!!.getToken()!!
            //如果不存在就会创建这个table
            //todo test 是否需要drop掉原来的那个表
            this@LoginActivity.database.use {
              createTable(CLASSED_IDS, true,
                  "id" to INTEGER) }

            //覆盖掉之前的那个表
            for(i in t.getClasses_id()!!) {
              this@LoginActivity.database.use{
                insert(CLASSED_IDS,"id" to i)
              }
            }

            //储存老师当前地班级
            mCurrentId = t.getClasses_id()!!.last()
          }

          override fun onCompleted() {}

          override fun onError(e: Throwable?) {
            e!!.printStackTrace()
            showErrorSnackbarShort("等落失败")
          }
        })
  }

  private val mParentApi :()->Unit = {
    val loginBody = ParentLoginBody(password = mEdtInputPassword.editableText.toString(),
        sid = mEdtInputNumber.editableText.toString())

    NetFactory
        .retrofitService
        .parentLogin(loginBody = loginBody)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object:Subscriber<ParentLoginToken>(){
          override fun onNext(t: ParentLoginToken?) {
            mCurrentId = t!!.getClass_id()
            mToken = t.getToken()!!
          }

          override fun onCompleted() {}

          override fun onError(e: Throwable?) {
            e!!.printStackTrace()
            showErrorSnackbarShort("登录失败")
          }
        })
  }


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
          mTeacherApi()
        }
        USER_PARENT ->{
          mParentApi()
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

