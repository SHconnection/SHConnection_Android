package com.example.kolibreath.shconnection.ui.auth

import USER_PARENT
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.ui.BaseActivity
import com.example.kolibreath.shconnection.base.ui.ToolbarActivity
import com.example.kolibreath.shconnection.extensions.REQUEST_CODE
import com.example.kolibreath.shconnection.extensions.findView
import com.example.kolibreath.shconnection.extensions.isGranted
import com.example.kolibreath.shconnection.extensions.requestPermissions
import com.example.kolibreath.shconnection.ui.main.TeacherCreateClassActivity

class NavigationActivity:BaseActivity(){

  override fun onCreate(
    savedInstanceState: Bundle?
  ) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_navigation)
    initView()

    this.requestPermissions()
  }

  /**
   * override of permission result
   */

  override fun onRequestPermissionsResult(
    requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    when(requestCode){
      REQUEST_CODE ->{
        if(isGranted(grantedResult = grantResults)){
          Log.d("SHConnection","请求成功")
        }
      }
    }
  }

  private  fun initView(){
    findView<Button>(R.id.btn_login_directly)
        .value.setOnClickListener{
      LoginActivity.start(
          this@NavigationActivity)
    }

    findView<Button>(R.id.btn_join_new_class)
        .value.setOnClickListener{
      JoinClassActivity.start(this@NavigationActivity)
    }

    findView<Button>(R.id.btn_create_class).value.setOnClickListener{
      TeacherCreateClassActivity.start(this@NavigationActivity)
    }
  }
}