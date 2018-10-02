package com.example.kolibreath.shconnection.ui.main.profile

import PROFILE_TYPE
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.TeacherInfoData
import com.example.kolibreath.shconnection.base.ui.ToolbarActivity
import com.example.kolibreath.shconnection.extensions.findView

class UserProfileActivity:ToolbarActivity() {

  //todo 需要新增条目 ： 微信
  private lateinit var mUserProfile : TeacherInfoData

  private var TYPE :Int  = UserProfileAdapter.MINE_PROFILE

  private val mIvAvatar: ImageView by findView(R.id.iv_avatar)
  private val mRvRecycler by findView<RecyclerView>(R.id.rv_items)

  private fun initViews(){
    mIvAvatar.setOnClickListener {
      //todo after click on the avatar
    }
    val adapter = UserProfileAdapter(TYPE,userProfile = mUserProfile)
    mRvRecycler.layoutManager = LinearLayoutManager(this)
    mRvRecycler.adapter = adapter
  }

  override fun onCreate(
    savedInstanceState: Bundle?
  ) {
    super.onCreate(savedInstanceState)

    TYPE = intent.getIntExtra(PROFILE_TYPE,UserProfileAdapter.MINE_PROFILE)
    //todo try to get user profile value
    initViews()
  }

}