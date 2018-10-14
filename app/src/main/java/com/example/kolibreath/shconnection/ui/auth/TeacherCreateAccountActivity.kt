package com.example.kolibreath.shconnection.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.MainTeacherSignUpBody
import com.example.kolibreath.shconnection.base.net.NetFactory
import com.example.kolibreath.shconnection.base.ui.BaseActivity
import com.example.kolibreath.shconnection.extensions.showSnackbar
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class TeacherCreateAccountActivity:BaseActivity(){


  companion object {
    fun start(context:Context){
      context.startActivity(Intent( context,TeacherCreateAccountActivity::class.java))
    }
  }
  private lateinit var mEdtName:EditText
  private lateinit var mEdtNumber:EditText
  private lateinit var mEdtPassword:EditText

  private lateinit var mBtnConfirm :Button

  val EditText.content
    get() = this.text.toString()

  private fun initView(){
    mEdtName = findViewById(R.id.edt_mainteacher_name)
    mEdtNumber = findViewById(R.id.edt_mainteacher_number)
    mEdtPassword = findViewById(R.id.edt_mainteacher_password)

    mBtnConfirm = findViewById(R.id.btn_confirm)
    mBtnConfirm.setOnClickListener {
      val mainTeacherSignUpBody = MainTeacherSignUpBody(
          wid = mEdtName.content,
          password = mEdtPassword.content,
          name = mEdtName.content)

      NetFactory.retrofitService
          .signUpMainTeacher(mainTeacherSignUpBody = mainTeacherSignUpBody)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(object:Subscriber<Any>(){
            override fun onNext(t: Any?) {}

            override fun onCompleted() {
              showSnackbar("注册成功"){
                this@TeacherCreateAccountActivity.finish()
                LoginActivity.start(this@TeacherCreateAccountActivity)
              }
            }

            override fun onError(e: Throwable?) {
              e!!.printStackTrace()
            }
          })
    }

  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_teacher_create_account)
    initView()
  }
}
