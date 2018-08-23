package com.example.kolibreath.shconnection

import android.graphics.Bitmap
import android.os.Bundle
import com.uuzuche.lib_zxing.activity.CaptureFragment
import com.uuzuche.lib_zxing.activity.CodeUtils
import com.uuzuche.lib_zxing.activity.CodeUtils.AnalyzeCallback
import java.io.File

class ScanActivity : ToolbarActivity(){

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_scan)

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
      CodeUtils.setFragmentArgs(this,R.layout.fragment_scan)
      analyzeCallback = analyzeCb
    }
  }
}