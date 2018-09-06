package com.example.kolibreath.shconnection.ui.main.news

import IMG_LIST
import MAX_SELECT_PIC_NUM
import POSITION
import REQUEST_CODE_MAIN
import RESULT_CODE_VIEW_IMG
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowId
import android.widget.AdapterView
import android.widget.GridView
import com.example.kolibreath.shconnection.R
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.entity.LocalMedia
import org.jetbrains.anko.find
import org.jetbrains.anko.gridView

class ViewPictureActivity: AppCompatActivity(){

  private val mContext = this
  private lateinit var mGridView: GridView
  private val mPicList = ArrayList<String>()

  private lateinit var mGridViewAdapter : GridViewAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_view_picture_activity)

    initGridView()
  }

  override fun onActivityResult(
    requestCode: Int,
    resultCode: Int,
    data: Intent?
  ) {
    super.onActivityResult(requestCode, resultCode, data)
    if(resultCode == Activity.RESULT_OK){
      when (requestCode){
        PictureConfig.CHOOSE_REQUEST ->{
          refreshAdapter(PictureSelector.obtainMultipleResult(data))
        }
      }
    }
    if(requestCode == REQUEST_CODE_MAIN && resultCode == RESULT_CODE_VIEW_IMG){
      val toDeletePicList = data!!.getStringArrayListExtra(IMG_LIST)
      mPicList.clear()
      //添加所有的路径到这里
      mPicList.addAll(toDeletePicList)
      mGridViewAdapter.notifyDataSetChanged()
    }
  }

  /**
   * 在选择了图片之后更新GridView中的内容
   */
  private fun refreshAdapter(picList:List<LocalMedia>){
    for ( media in picList)
      if(media.isCompressed){
        mPicList.add(media.compressPath)
        mGridViewAdapter.notifyDataSetChanged()
      }
  }

  private fun viewPluImg(position:Int){
    val intent = Intent(mContext,PlusImageActivity::class.java)
    intent.putStringArrayListExtra(IMG_LIST,mPicList)
    intent.putExtra(POSITION,position)
    startActivityForResult(intent,REQUEST_CODE_MAIN)
  }

  private fun selectPic(maxTotal: Int){
    PictureSelectorConfig.initMultiConfig(this,maxTotal)
  }

  private fun initGridView() {
    mGridViewAdapter =  GridViewAdapter(mContext,mPicList)
    mGridView = findViewById(R.id.gridView)
    mGridView.adapter = mGridViewAdapter
    mGridView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
      if(position == parent.childCount - 1) {
        if (mPicList.size == MAX_SELECT_PIC_NUM)
          viewPluImg(position)
        else
          selectPic(MAX_SELECT_PIC_NUM - mPicList.size)
      }else
        viewPluImg(position)
    }
  }
}