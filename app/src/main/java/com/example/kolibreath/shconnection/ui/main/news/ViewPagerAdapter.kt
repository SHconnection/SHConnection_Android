package com.example.kolibreath.shconnection.ui.main.news

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.extensions.createView
import com.luck.picture.lib.photoview.PhotoView

/**
 * ViewPager 的Adapter
 */
//imageList 图片的数据源
class ViewPagerAdapter(val context:Context,val imageList :List<String>): PagerAdapter() {



  override fun getItemPosition(`object`: Any): Int {
    return POSITION_NONE
  }

  override fun getCount(): Int {
    return imageList.size
  }


  //返回一个object 对象
  //todo 浏览大图有点问题
  override fun instantiateItem(
    container: ViewGroup,
    position: Int
  ): Any {
    val itemView = getItemView(R.layout.view_plus_image)
    val photoView : PhotoView = itemView.findViewById(R.id.iv_img)
    Glide.with(context).load(imageList[position]).into(photoView)
    container.addView(photoView)
    return itemView
  }

  override fun destroyItem(
    container: ViewGroup,
    position: Int,
    `object`: Any
  ) {
    container.removeView(`object` as View?)
  }

  override fun isViewFromObject(
    view: View,
    `object`: Any
  ): Boolean {
    return view == `object`
  }



  private fun getItemView(layoutId:Int):View{
    return context.createView(layoutId)
  }


}