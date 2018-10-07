package com.example.kolibreath.shconnection.ui.main.profile

import USER_NONE
import USER_TYPE
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.ui.ToolbarActivity
import com.example.kolibreath.shconnection.extensions.getValue

class UserProfileActivity: ToolbarActivity(){

  private var mUserType = this.getValue(USER_TYPE,USER_NONE)
  private var names = arrayListOf<String>("个人资料","加入新的班级","注销帐号")

  private var mUserProfileAdapter = UserProfileAdapter(names)

  private lateinit var mRecyclerView: RecyclerView

  private fun initView(){
    mRecyclerView = findViewById(R.id.rv_items)
    val layoutManager = LinearLayoutManager(this@UserProfileActivity)
    mRecyclerView.layoutManager = layoutManager
    mRecyclerView.adapter = mUserProfileAdapter
  }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_user_profile)

    initView()
    mUserType = getValue(USER_TYPE,USER_NONE)
  }
}