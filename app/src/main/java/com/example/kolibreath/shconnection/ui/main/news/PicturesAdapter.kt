package com.example.kolibreath.shconnection.ui.main.news

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.extensions.createView
import com.example.kolibreath.shconnection.ui.main.news.PicturesAdapter.PicturesViewHolder
import org.jetbrains.anko.image
import java.text.FieldPosition

class PicturesAdapter(private val mPictures:List<Bitmap>): RecyclerView.Adapter<PicturesViewHolder>(){

  override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PicturesViewHolder {
    val view = p0.context.createView(R.layout.item_pictures)
    val viewHolder = PicturesViewHolder(view)

    return viewHolder
  }

  override fun onBindViewHolder(viewHolder: PicturesViewHolder, position: Int) {
    for((counter, view) in viewHolder.viewList.withIndex()){
      view.setImageBitmap(mPictures[counter])
    }
  }

  override fun getItemCount(): Int = mPictures.size / 3



  class PicturesViewHolder constructor(itemView:View)
    : RecyclerView.ViewHolder(itemView){

    val mLinearLayout = itemView.findViewById<LinearLayout>(R.id.pictures_layout)
    val mIvPicture1   = itemView.findViewById<ImageView>(R.id.iv_picture_1)
    val mIvPicture2   = itemView.findViewById<ImageView>(R.id.iv_picture_2)
    val mIvPicture3   = itemView.findViewById<ImageView>(R.id.iv_picture_3)

    val viewList = ArrayList<ImageView>()

    init {
      initView()
    }

    fun initView(){
      viewList.add(mIvPicture1)
      viewList.add(mIvPicture2)
      viewList.add(mIvPicture3)
    }
  }
}