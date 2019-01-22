package com.example.kolibreath.shconnection.ui.main.profile

import LOGIN_TOKEN
import USER_PARENT
import USER_TEACHER
import USER_TYPE
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.widget.EditText
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.Profile
import com.example.kolibreath.shconnection.base.net.NetFactory
import com.example.kolibreath.shconnection.base.ui.ToolbarActivity
import com.example.kolibreath.shconnection.extensions.getValue
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * 1.用户类型默认为老师
 *
 * 2.这个参数的key = modifiable
 * 如果传入modifiable == true 则可以修改
 */
class UserProfileActivity : ToolbarActivity(){


    lateinit var edtUserName :EditText
    lateinit var edtUserTel :EditText
    lateinit var edtUserWechat:EditText
    lateinit var edtUserIntro :EditText
    lateinit var edtUserSubject :EditText
    lateinit var edtUserRate :EditText

    companion object {
        fun start(context:Context){
            context.startActivity(Intent(context,UserProfileActivity::class.java))
        }
    }


    //用户类型
    var mUserType = getValue(USER_TYPE,USER_TEACHER)

    private fun initView(modifiable : Boolean) {
        edtUserName = findViewById(R.id.edt_name)

        edtUserTel = findViewById(R.id.edt_tel)

        edtUserWechat = findViewById(R.id.edt_wechat)

        edtUserIntro = findViewById(R.id.edt_intro)

        edtUserSubject = findViewById(R.id.edt_subject)

        edtUserRate = findViewById(R.id.edt_rate)

        if (modifiable) {
            edtUserTel.isFocusable = false
            edtUserWechat.isFocusable = false
            edtUserIntro.isFocusable = false
            edtUserSubject.isFocusable = false
            edtUserName.isFocusable = false
            edtUserRate.isFocusable = false

        }
    }

    //todo 根据api文档拿到相关的数据结构
    private fun getProfile(userType :Int){
        when(userType){
            USER_TEACHER ->{
              NetFactory.retrofitService
                      .teacherProfile(getValue(LOGIN_TOKEN,""))
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .subscribe(object:Subscriber<Profile>(){
                          override fun onNext(t: Profile?) {

                          }

                          override fun onCompleted() {
                          }

                          override fun onError(e: Throwable?) {
                          }
                      })
            }
            USER_PARENT ->  {

            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        //初始化
        initView(intent.getBooleanExtra("modifiable",false))
    }
}