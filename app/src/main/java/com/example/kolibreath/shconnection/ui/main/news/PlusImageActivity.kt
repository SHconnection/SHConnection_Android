package com.example.kolibreath.shconnection.ui.main.news

import IMG_LIST
import POSITION
import RESULT_CODE_VIEW_IMG
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.widget.CancelOrOkDialog
import com.example.kolibreath.shconnection.extensions.findView
import java.text.FieldPosition

class PlusImageActivity : AppCompatActivity(), ViewPager.OnPageChangeListener,View.OnClickListener{

  private lateinit var mImgList : ArrayList<String>
  private val mTvPosition by findView<TextView>(R.id.tv_position)
  private var mPosition: Int = 0

  private lateinit var mViewPager : ViewPager
  private lateinit var mAdapter : ViewPagerAdapter

  /**
   * 初始view 设置 照片的位置
   */
  private fun initView(){
    mViewPager = findViewById(R.id.viewpager)

    findViewById<ImageView>(R.id.iv_back).setOnClickListener(this)
    findViewById<ImageView>(R.id.iv_delete).setOnClickListener(this)

    mAdapter  = ViewPagerAdapter(this,mImgList)
    mViewPager.adapter = mAdapter

    mTvPosition.text = formatTitle()
    mViewPager.currentItem  = mPosition
  }

  override fun onPageScrollStateChanged(p0: Int) {
    //not implemented!
  }

  override fun onPageScrolled(
    p0: Int,
    p1: Float,
    p2: Int
  ) {
    //not implemented
  }

  override fun onClick(view: View?) {
    when(view!!.id){
      R.id.iv_back ->{
        back()
      }
      R.id.iv_delete ->{
        deletePicture()
      }
    }
  }

  override fun onPageSelected(position: Int) {
    mPosition = position
    mTvPosition.text = formatTitle()
  }

  /**
   * 更新位置显示的内容 比如 m/n-1 张照片
   */
  private fun validatePositionText(){
    mTvPosition.text = formatTitle()
    mViewPager.currentItem = mPosition
    mAdapter.notifyDataSetChanged()
  }

  /**
   * 生成格式化的字符串 position text
   */

  private fun formatTitle():String = "${mPosition+1}/${mImgList.size}"

  private fun deletePicture(){
    val dialog = CancelOrOkDialog(this,"确定删除这张照片吗"
    ,cancelListener = {
      //todo 暂时没有实现
    },confirmListener = {
      mImgList.removeAt(mPosition)
      validatePositionText()
    })
    dialog.show()
  }

  /**
   * return to the previous page
   * 并且将选择完成的图片代码返回
   */
  private fun back(){
    val intent = intent
    intent.putStringArrayListExtra(IMG_LIST,mImgList)
    setResult(RESULT_CODE_VIEW_IMG,intent)
    finish()
  }

  override fun onKeyDown(
    keyCode: Int,
    event: KeyEvent?
  ): Boolean {
    if(keyCode == KeyEvent.KEYCODE_BACK){
      back()
      return true;
    }
    return super.onKeyDown(keyCode, event)
  }

  /**
   * 初始化imagelist 初始化 开始的Position
   */
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_plus_image)

    mImgList = intent.getStringArrayListExtra(IMG_LIST)
    mPosition = intent.getIntExtra(POSITION,0)

    initView()
  }
}