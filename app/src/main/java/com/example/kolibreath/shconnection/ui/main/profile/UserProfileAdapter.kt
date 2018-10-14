package com.example.kolibreath.shconnection.ui.main.profile

import CLASS_ID
import LOGIN_TOKEN
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.R.layout
import com.example.kolibreath.shconnection.extensions.createView
import com.example.kolibreath.shconnection.extensions.putValue
import com.example.kolibreath.shconnection.ui.auth.JoinClassActivity
import com.example.kolibreath.shconnection.ui.auth.NavigationActivity
import java.util.LinkedList

class UserProfileAdapter(val list: ArrayList<String>) : Adapter<ViewHolder>(){

  private lateinit var mCtx :Context
  private val PROFILE_DETAIL = 0
  private val JOIN_CLASS = 1
  private val LOG_OUT = 2

  override fun getItemCount(): Int {
    return list.size
  }

  override fun onBindViewHolder(
    viewholder: android.support.v7.widget.RecyclerView.ViewHolder,
    position: Int
  ) {
    (viewholder as UserProfileAdapter).mItemName.text = list[position]
    viewholder.mItemName.setOnClickListener {
      when(position){
      //todo 个人资料具体界面
        PROFILE_DETAIL -> UserProfileDetailActivity.start(mCtx)
        JOIN_CLASS -> JoinClassActivity.start(mCtx)
        LOG_OUT ->{
          //清除token 和 班级id
          mCtx.putValue(LOGIN_TOKEN,"")
          mCtx.putValue(CLASS_ID,"")
          NavigationActivity.start(mCtx)
        }
      }
    }
  }

  override fun onCreateViewHolder(
    p0: ViewGroup,
    p1: Int
  ): android.support.v7.widget.RecyclerView.ViewHolder {
    mCtx = p0.context
    return UserProfileAdapter(mCtx.createView(layout.view_item_user_profile))
  }

  class UserProfileAdapter (val view: View): RecyclerView.ViewHolder(view){

     var mItemName :TextView = view.findViewById(R.id.tv_item_name)

  }
}