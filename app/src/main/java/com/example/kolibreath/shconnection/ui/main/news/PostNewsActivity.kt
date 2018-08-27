package com.example.kolibreath.shconnection.ui.main.news

import REQUEST_CODE_IMAGE_CAPTURE
import TAG_INFOMATION
import TAG_NEWS
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.ui.BaseActivity
import com.example.kolibreath.shconnection.extensions.findView
import com.example.kolibreath.shconnection.extensions.setBgColor
import com.example.kolibreath.shconnection.extensions.setTxColor

@RequiresApi(VERSION_CODES.M)
class PostNewsActivity: BaseActivity(){

  /**
   * originState =1 表示两种类型的颜色是白黑 originState = 0 表示两种类型的颜色是黑白
   */
  private var mOriginState = 1

  /**
   * 默认的TAG 是 news
   */
  private var mTag = TAG_NEWS

  /**
   * 完成的内容content
   */
  private var mContent:String?  = null

  /**
   * 图片的list
   */
  //todo change the type of the list
  private val mPicutures = ArrayList<Bitmap>()

  private val mTvContent by findView<EditText>(R.id.tv_content)
  private val mBtnCancel by findView<TextView>(R.id.btn_cancel)
  private val mBtnConfirm by findView<TextView>(R.id.btn_confirm)
  private val mBtnSwitchActivity by findView<TextView>(R.id.btn_activity)
  private val mBtnSwitchInformation by findView<TextView>(R.id.btn_information)
  private val mBtnPicture by findView<ImageView>(R.id.btn_picture)
  private val mRvPicutures by findView<RecyclerView>(R.id.rv_pictures)

  private fun setListeners(){
    mTvContent.addTextChangedListener(object :TextWatcher{
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(
          p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(
          p0: CharSequence?, start: Int, before: Int, count: Int) {
          if(count == 0)
            mTvContent.setText("说点什么吧")

          mBtnCancel.setTxColor(R.color.white)
          mContent = p0.toString()
        }
      })

    mBtnSwitchActivity.setOnClickListener {
      if (mOriginState == 0) {
        mBtnSwitchActivity.setBgColor(R.color.black)
        mBtnSwitchActivity.setTxColor(R.color.white)
        mOriginState  = 1
        mTag = TAG_NEWS
      }
    }

    mBtnSwitchInformation.setOnClickListener {
      if (mOriginState == 1) {
        mBtnSwitchInformation.setBgColor((R.color.white))
        mBtnSwitchInformation.setTxColor((R.color.black))
        mOriginState = 0
        mTag = TAG_INFOMATION
      }
    }

    mBtnPicture.setOnClickListener {
      //todo open gallery or open album and add pictures
    }

    mBtnConfirm.setOnClickListener {
    }

    mBtnCancel.setOnClickListener {  finish()}
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main_post_news)

    setListeners()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when(requestCode){
      REQUEST_CODE_IMAGE_CAPTURE -> {

      }
    }
  }
}