package com.example.kolibreath.shconnection.ui.main.profile

import ID
import LOGIN_TOKEN
import PROFILE_TYPE
import USER_TYPE
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ItemDecoration
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.Profile
import com.example.kolibreath.shconnection.base.net.NetFactory
import com.example.kolibreath.shconnection.base.ui.ToolbarActivity
import com.example.kolibreath.shconnection.extensions.getValue
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.LinkedList

class UserProfileDetailActivity :ToolbarActivity(){

  companion object {
    fun start(context:Context){
      context.startActivity(Intent(context,UserProfileDetailActivity::class.java))
    }
  }

  private val names :LinkedList<String>
  get() {
    val temp = arrayListOf<String>("姓名","电话","微信","简介","头像","科目")
    val n = LinkedList<String>()
    temp.forEach{
      n.add(it)
    }
    return n
  }

  //当前戳进去地这个人的
  private val mProfileId = intent.getIntExtra(PROFILE_TYPE,0)

  private val id = getValue(ID,"")
  private val token = getValue(LOGIN_TOKEN,"")

  private lateinit var mRecyclerView: RecyclerView

  private val values :LinkedList<String>
  get() {
    if(id == "" || token == "")
      return emptyList<String>() as LinkedList<String>

    val linkedList = LinkedList<String>()
    NetFactory.retrofitService
        .teacherProfile(tid = id,token = token)
        .observeOn(Schedulers.io())
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe(object:Subscriber<Profile>(){
          override fun onNext(t: Profile?) {
            if(t!= null){
              linkedList.apply {
                add(t.name)
                add(t.tel)
                add(t.wechat)
                add(t.intro)
                add(t.avatar)
                add(t.subject)
              }
            }
          }

          override fun onCompleted() {}
          override fun onError(e: Throwable?) {e?.printStackTrace() }
        })

    return linkedList
  }

  private val mUserDetailAdapter = UserProfileDetailAdapter(
      names = names,values = values,isMyProfile = id.toInt() == mProfileId
  )

  private fun initView(){
    mRecyclerView = findViewById(R.id.rv_details)
    mRecyclerView.adapter = mUserDetailAdapter
    mRecyclerView.layoutManager = LinearLayoutManager(this@UserProfileDetailActivity)
    val divider  = DividerItemDecoration(this@UserProfileDetailActivity,DividerItemDecoration.VERTICAL)
    divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.item_divider)!!)
    mRecyclerView.addItemDecoration(divider)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_user_profile_detail)

    if(id.toInt() == mProfileId){
      names.add("修改个人资料")
      values.add("点击此处修改")
    }

    initView()
  }
}