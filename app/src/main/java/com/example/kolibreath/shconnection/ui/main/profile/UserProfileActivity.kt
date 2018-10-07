package com.example.kolibreath.shconnection.ui.main.profile

import ID
import LOGIN_TOKEN
import USER_NONE
import USER_PARENT
import USER_TEACHER
import USER_TYPE
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.widget.ImageView
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.App
import com.example.kolibreath.shconnection.base.Profile
import com.example.kolibreath.shconnection.base.net.NetFactory
import com.example.kolibreath.shconnection.base.ui.ToolbarActivity
import com.example.kolibreath.shconnection.extensions.getValue
import com.squareup.picasso.Picasso
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class UserProfileActivity: ToolbarActivity(){

  companion object {
    fun start(context:Context){
      context.startActivity(Intent(context,UserProfileActivity::class.java))
    }
  }
  private var mUserType = App.getContext().getValue(USER_TYPE,USER_NONE)

  private val id = App.getContext().getValue(ID,"")
  private val token = App.getContext().getValue(LOGIN_TOKEN,"")

  private var names = arrayListOf<String>("个人资料","加入新的班级","注销帐号")
  private var mUserProfileAdapter = UserProfileAdapter(names)

  //views
  private lateinit var mIvAvatar:ImageView
  private lateinit var mRecyclerView: RecyclerView

  private fun initView(){
    mRecyclerView = findViewById(R.id.rv_items)
    val layoutManager = LinearLayoutManager(this@UserProfileActivity)
    mRecyclerView.layoutManager = layoutManager
    mRecyclerView.adapter = mUserProfileAdapter

    mIvAvatar = findViewById(R.id.iv_avatar)
    Picasso.get().load(R.mipmap.ic_launcher).into(mIvAvatar)
  }

  private fun getProfile(){
    var observale:Observable<Profile> = when(mUserType){
      USER_PARENT ->{
        NetFactory.retrofitService
            .teacherProfile(tid = id,token = token)
      }
      USER_TEACHER ->{
        NetFactory.retrofitService
            .parentProfile( pid= id,token = token)
      }else -> {
        throw  Exception("用户类型初始化错误")
      }
    }

    observale.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(object : Subscriber<Profile>() {
          override fun onNext(t: Profile?) {
            if(TextUtils.isEmpty(t!!.avatar))
            Picasso.get().load(t.avatar)
            else
            Picasso.get().load(t.avatar)
          }

          override fun onCompleted() {}

          override fun onError(e: Throwable?) {
            e!!.printStackTrace()
          }
        })

  }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_user_profile)

    initView()
    mUserType = getValue(USER_TYPE,USER_NONE)
    getProfile()
  }
}