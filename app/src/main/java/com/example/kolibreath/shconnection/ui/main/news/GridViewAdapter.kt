package com.example.kolibreath.shconnection.ui.main.news

import MAX_SELECT_PIC_NUM
import android.annotation.SuppressLint
import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.kolibreath.shconnection.R
import org.jetbrains.anko.imageResource
import java.util.zip.Inflater

class GridViewAdapter(
  private val context:Context,
  private val mList:List<String>?): BaseAdapter() {

  private var mInflater : LayoutInflater = LayoutInflater.from(context)

  //返回一个对象但是由于list 可以为空 一定要反会非空对象
  override fun getItem(position: Int): Any {
    return mList!![position]
  }

  override fun getCount(): Int {
    val count = if (mList == null ) 1 else mList.size + 1
    return if(count > MAX_SELECT_PIC_NUM) mList!!.size else count
  }

  override fun getItemId(p0: Int): Long {
    return p0.toLong()
  }

  @SuppressLint("ViewHolder")
  override fun getView(
    position: Int,
    convertView: View?,
    parent: ViewGroup?
  ): View {
    //todo remove the ic launcher as the backgound of the add button
   val resultView = mInflater.inflate(R.layout.view_grid_item,parent,false)
    val iv = resultView.findViewById<ImageView>(R.id.iv_picture)
    if(position < mList!!.size)
      Glide.with(context).load(mList[position]).into(iv)
    else
      iv.imageResource = R.drawable.ic_launcher_background

    return resultView
  }
}