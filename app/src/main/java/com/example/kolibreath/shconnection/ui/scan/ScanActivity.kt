package com.example.kolibreath.shconnection.ui.scan

import JOIN_CLASS_ACTIVITY
import ROUTER_NAME
import SCAN_RESULT
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.support.annotation.RequiresApi
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.R.layout
import com.example.kolibreath.shconnection.base.ui.ToolbarActivity
import com.example.kolibreath.shconnection.extensions.encrypt
import com.example.kolibreath.shconnection.extensions.showSnackBarShort
import com.example.kolibreath.shconnection.ui.auth.JoinClassActivity
import com.uuzuche.lib_zxing.activity.CaptureFragment
import com.uuzuche.lib_zxing.activity.CodeUtils
import com.uuzuche.lib_zxing.activity.CodeUtils.AnalyzeCallback

class ScanActivity : ToolbarActivity(){

  //表示从哪个Activity 路由过来的值 暂时初始化为 -1
  private  var mRouter : Int = -1
  companion object {

    fun start(context:Context){
      context.startActivity(Intent(context,ScanActivity::class.java))
    }

    fun start(context: Context, routerValue:Int){
      val intent = Intent(context,ScanActivity::class.java)
      intent.putExtra(ROUTER_NAME,routerValue)
      context.startActivity(intent)
    }
  }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_scan)

    mRouter = intent.getIntExtra(ROUTER_NAME,-1)

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

        fun <T> getIntent(`class`:Class<T>):Intent  = Intent(this@ScanActivity,`class`).apply {
          putExtra(SCAN_RESULT,encrypt(result!!)) }

        when(mRouter){
          JOIN_CLASS_ACTIVITY ->{
            this@ScanActivity.startActivity(getIntent(`class` = JoinClassActivity::class.java))
          }

          else ->{
            throw IllegalArgumentException("扫描完成后无法路由到正确的类")
          }
        }
        showSnackBarShort("成功")
        finish()
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