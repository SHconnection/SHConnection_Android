package com.example.kolibreath.shconnection.ui.main.profile

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.ui.ToolbarActivity
import com.example.kolibreath.shconnection.extensions.findView
import org.jetbrains.anko.find

class UserProfileActivity:ToolbarActivity() {

  private val mIvAvatar: ImageView by findView(R.id.iv_avatar)
  private val mRvRecycler by findView<RecyclerView>(R.id.rv_items)

  private fun setListeners(){
    mIvAvatar.setOnClickListener {
      //todo after click on the avatar
    }


  }

  override fun onCreate(
    savedInstanceState: Bundle?
  ) {
    super.onCreate(savedInstanceState)
  }

}