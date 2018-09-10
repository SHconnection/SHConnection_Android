package com.example.kolibreath.shconnection.ui.main.profile

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.data.TeacherInfoData
import com.example.kolibreath.shconnection.extensions.createView
import com.example.kolibreath.shconnection.ui.main.profile.UserProfileAdapter.ViewHolder

//todo 加上divider
class UserProfileAdapter constructor(mType:Int,userProfile:TeacherInfoData) : RecyclerView.Adapter<ViewHolder>(){

  /**
   * 定义两种类型 一种是自己 一种是别人
   * 自己可以看到： 名称 电话 简介 收藏 评论
   * 别人可以看到： 名称 电话 简介
   */
  companion object {
    const val MINE_PROFILE = 1
    const val OTHER_PROFILE = 2
  }

  private lateinit var mContext : Context

  private val mType = mType;
  private val mUserProfile:TeacherInfoData = userProfile

  private var mStartList : List<String>? = null
  private var mCommentList : List<String>? = null

  private var mNameValueMap = ArrayList<Pair<String,String>>()

  private fun initProfile(userProfile: TeacherInfoData){
    val phone:Pair<String,String> = Pair("电话",userProfile.tel)
    val name:Pair<String,String> = Pair("名称",userProfile.name)
    val description:Pair<String,String> = Pair("简介",userProfile.intro)

    mNameValueMap = arrayListOf(name,phone,description)

  }

  init {
    when(mType){
      MINE_PROFILE -> {
        initProfile(userProfile)
        mStartList = userProfile.star
        mCommentList = userProfile.comment
      }
      OTHER_PROFILE -> {
        initProfile(userProfile)
      }
    }
  }
  override fun getItemCount(): Int {
    return when(mType){
      MINE_PROFILE -> {
        3 + 2
      }else -> {
        3
      }
    }
  }

  override fun getItemViewType(position: Int): Int =  mType

  override fun onCreateViewHolder(
    viewParent: ViewGroup,
    viewType: Int
  ): ViewHolder {
    var view: View = when(viewType) {
      MINE_PROFILE -> {
        mContext.createView(R.layout.view_item_user_profile_1)
      }else -> {
        mContext.createView(R.layout.view_item_user_profile_2)
      }
    }
    return ViewHolder(view)
  }

  /**
   * 这里例子就能看出 Kotlin很方便的减少Java 空判断
   *
   * 如果position <= 2的话 就会使用list以内的 这个时候 viewHolder中的两种View都被实例化完成了
   * 当 position >= 3时 后面两个item 以此是 收藏和 评论
   * onCreateViewHolder()会根据mType返回不同的类型
   */
  override fun onBindViewHolder(
    holder: ViewHolder,
    position: Int
  ) {
    if(position <= 2){
      holder.mTvName.text = mNameValueMap[position].first
      holder.mTvValue?.text = mNameValueMap[position].second
    }else{
      holder.getHoldingView().setOnClickListener {
        navigate(type = position)
      }
      when(position){
        4 ->{
          holder.mTvName.text = "收藏"
        }
        5 -> {
          holder.mTvName.text = "评论"
        }
      }
    }
  }

  private fun navigate(type:Int){
    //todo navigate by the type/position
    when(type){
      4 ->{
        //收藏
      }
      5 ->{
        //评论
      }
    }
  }

  class ViewHolder(val view:View): RecyclerView.ViewHolder(view){

    /**
     * 需要使用两个xml 初始化这个view 参数
     * 如果是button类型的view的话 tvalue可以为null
     */
    var mTvName : TextView = view.findViewById((R.id.tv_item_name))
    var mTvValue: TextView? = view.findViewById(R.id.tv_item_value)

    fun getHoldingView():View{
      return view
    }
  }
}