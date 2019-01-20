package com.example.kolibreath.shconnection.ui.main.news

import CLASS_ID
import ID
import IMG_LIST
import LOGIN_TOKEN
import MAX_SELECT_PIC_NUM
import POSITION
import REQUEST_CODE_MAIN
import RESULT_CODE_VIEW_IMG
import TAG_INFOMATION
import TAG_NEWS
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.AdapterView
import android.widget.EditText
import android.widget.GridView
import android.widget.TextView
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.extensions.findView
import com.example.kolibreath.shconnection.extensions.getValue
import com.example.kolibreath.shconnection.extensions.setBgColor
import com.example.kolibreath.shconnection.extensions.setTxColor
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.entity.LocalMedia
import java.util.LinkedList
import kotlin.collections.ArrayList
import kotlin.collections.List

//发送一张新的动态
class ViewPictureActivity: AppCompatActivity(){

  companion object {
    fun start(context: Context){
      context.startActivity(Intent(context,ViewPictureActivity::class.java))
    }
  }

  private val teacherId = getValue(ID,"")
  private val token = getValue(LOGIN_TOKEN,"")
  private val classid = getValue(CLASS_ID,"")

  private val mContext = this
  private lateinit var mGridView: GridView
  private val mPicList = ArrayList<String>()

  private lateinit var mGridViewAdapter : GridViewAdapter

  /**
   * originState =1 表示两种类型的颜色是白黑 originState = 0 表示两种类型的颜色是黑白
   * 0 表示选中消息 1 表示选中动态
   * 白色表示选中
   */
  private var mOriginState = 0

  /**
   * 默认的TAG 是 news
   */
  private var mTag = TAG_NEWS

  /**
   * 完成的内容content
   */
  private var mContent:String?  = null

  private val mTvContent by findView<EditText>(R.id.tv_content)
  private val mBtnCancel by findView<TextView>(R.id.btn_cancel)
  private val mBtnConfirm by findView<TextView>(R.id.btn_confirm)
  private val mBtnSwitchActivity by findView<TextView>(R.id.btn_activity)
  private val mBtnSwitchInformation by findView<TextView>(R.id.btn_information)

  /**
   * 生成的urls
   */
  private lateinit var mPicUrls : LinkedList<String>

  @RequiresApi(VERSION_CODES.M)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_view_picture_activity)

    initGridView()
    setListeners()
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

  @RequiresApi(VERSION_CODES.M)
  private fun setListeners(){
    mTvContent.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(p0: Editable?) {}
      override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

      override fun onTextChanged(
        p0: CharSequence?, start: Int, before: Int, count: Int) {
        if(count == 0)
          mTvContent.setText("说点什么吧")

        mBtnConfirm.setTxColor(R.color.white)
        mContent = p0.toString()
      }
    })

    mBtnSwitchActivity.setOnClickListener {
      if (mOriginState == 1) {
        mBtnSwitchInformation.setBgColor(R.color.black)
        mBtnSwitchInformation.setTxColor(R.color.white)

        mBtnSwitchActivity.setTxColor(R.color.black)
        mBtnSwitchActivity.setBgColor(R.color.white)
        mOriginState  = 0
        mTag = TAG_NEWS
      }
    }

    mBtnSwitchInformation.setOnClickListener {
      if (mOriginState == 0) {
        mBtnSwitchInformation.setBgColor((R.color.white))
        mBtnSwitchInformation.setTxColor(R.color.black)

        mBtnSwitchActivity.setBgColor(R.color.black)
        mBtnSwitchActivity.setTxColor((R.color.white))
        mOriginState = 1
        mTag = TAG_INFOMATION
      }
    }

    mBtnConfirm.setOnClickListener {
      if(TextUtils.isEmpty(mContent))
        return@setOnClickListener
//
//      //todo 异步 ！！！！
//      QiniuExtension.postPictures(pictures = mPicList)
//          .subscribeOn(Schedulers.io())
//          .flatMap {
//            val feedBody = FeedBody(classId = classid.toInt()
//                ,teacherId = teacherId.toInt()
//                , type = mTag, content = mContent!!, picture_urls = it)
//            NetFactory.retrofitService
//                .postFeed(token = token, feedBody = feedBody  )
//                .subscribeOn(Schedulers.io())
//          }
//          .observeOn(AndroidSchedulers.mainThread())
//          .subscribe(object : Subscriber<Any>() {
//            override fun onNext(t: Any?) {}
//
//            override fun onCompleted() {
//              showSnackBarShort("发送成功")
//              MainActivity.start(this@ViewPictureActivity)
//            }
//
//            override fun onError(e: Throwable?) {
//              e!!.printStackTrace()
//            }
//          })

        //上传图片

    }

    mBtnCancel.setOnClickListener {  finish()}
  }

}