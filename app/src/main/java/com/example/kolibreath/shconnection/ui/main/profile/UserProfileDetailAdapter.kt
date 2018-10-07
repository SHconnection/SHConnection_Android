package com.example.kolibreath.shconnection.ui.main.profile

import android.content.Context
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.extensions.createView
import com.example.kolibreath.shconnection.ui.main.profile.UserProfileDetailAdapter.DetailViewHolder
import org.apache.xmlbeans.UserType
import java.util.LinkedList

//需要区分是自己的还是别人地
class UserProfileDetailAdapter
(val names:LinkedList<String>,
  val values:LinkedList<String>, val isMyProfile:Boolean): Adapter<DetailViewHolder>() {

  private lateinit var mCtx: Context

  //todo details

  override fun getItemCount(): Int {
    return names.size
  }

  override fun onBindViewHolder(
    viewHolder: DetailViewHolder,
    position: Int
  ) {
    viewHolder.mItemName.text = names[position]
    viewHolder.mItemValue.text = values[position]

    if(isMyProfile){
      if(position == names.size){
        //todo 修改个人资料页面
      }
    }
//    when()
  }
  override fun onCreateViewHolder(
    viewGroup: ViewGroup,
    position: Int
  ): DetailViewHolder {
    mCtx = viewGroup.context
    return DetailViewHolder(mCtx.createView(R.layout.view_item_user_profile_detail))
  }

  class DetailViewHolder(val view:View):ViewHolder(view){

   val mItemName = view.findViewById<TextView>(R.id.tv_item_name)!!
   val mItemValue = view.findViewById<TextView>(R.id.tv_item_value)!!
  }
}