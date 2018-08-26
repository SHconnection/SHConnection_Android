package com.example.kolibreath.shconnection.ui.auth

import android.graphics.Bitmap
import android.os.Bundle
import com.example.kolibreath.shconnection.R.layout
import com.example.kolibreath.shconnection.base.ui.ToolbarActivity
import com.example.kolibreath.shconnection.extensions.showSnackBarShort
import com.uuzuche.lib_zxing.activity.CaptureFragment
import com.uuzuche.lib_zxing.activity.CodeUtils
import com.uuzuche.lib_zxing.activity.CodeUtils.AnalyzeCallback

class ScanActivity : ToolbarActivity(){

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
      override fun onAnalyzeSuccess(mBitmap: Bitmap?, result: String?) {
        //todo 显示成功的结果
        showSnackBarShort("成功"){

        }
      }

      override fun onAnalyzeFailed() {
        //todo 显示失败的结果
      }
    }
    val captureFragment = CaptureFragment().apply {
      CodeUtils.setFragmentArgs(this, layout.fragment_scan)
      analyzeCallback = analyzeCb
    }
  }
}