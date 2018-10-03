package com.example.kolibreath.shconnection.ui.scan

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.support.annotation.RequiresApi
import com.example.kolibreath.shconnection.R.layout
import com.example.kolibreath.shconnection.base.RxBus
import com.example.kolibreath.shconnection.base.ScanEvent
import com.example.kolibreath.shconnection.base.ui.ToolbarActivity
import com.example.kolibreath.shconnection.extensions.encrypt
import com.example.kolibreath.shconnection.extensions.showSnackBarShort
import com.uuzuche.lib_zxing.activity.CaptureFragment
import com.uuzuche.lib_zxing.activity.CodeUtils
import com.uuzuche.lib_zxing.activity.CodeUtils.AnalyzeCallback

class ScanActivity : ToolbarActivity(){

  companion object {
    fun start(context:Context){
      context.startActivity(Intent(context,this::class.java))
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_scan)

    initView()
  }

  /**
   * 初始化 CaptureFragment
   */
  private fun initView(){
    val analyzeCb = object: AnalyzeCallback{

      @RequiresApi(VERSION_CODES.O)
      override fun onAnalyzeSuccess(mBitmap: Bitmap?, result: String?) {
        //如果扫描成功的话会返回班级id的String（加密之后）
        RxBus.getDefault().send(ScanEvent(this@ScanActivity.encrypt(result!!)))
        showSnackBarShort("成功")
      }

      override fun onAnalyzeFailed() {
        showSnackBarShort("失败")
      }
    }
    val captureFragment = CaptureFragment().apply {
      CodeUtils.setFragmentArgs(this, layout.fragment_scan)
      analyzeCallback = analyzeCb
    }
  }
}