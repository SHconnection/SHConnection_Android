package com.example.kolibreath.shconnection.ui.auth

import USER_PARENT
import USER_TEACHER
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.ui.ToolbarActivity
import com.example.kolibreath.shconnection.extensions.REQUEST_CODE
import com.example.kolibreath.shconnection.extensions.findView
import com.example.kolibreath.shconnection.extensions.isGranted
import com.example.kolibreath.shconnection.extensions.requestPermissions
import com.example.kolibreath.shconnection.ui.auth.`package`.ParentLoginActivity
import com.example.kolibreath.shconnection.ui.auth.teacher.TeacherLoginActivity

class NavigationActivity:ToolbarActivity(){

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
    findView<Button>(R.id.btn_teacher_access)
        .value.setOnClickListener{
      LoginActivity.start(
          this@NavigationActivity, USER_TEACHER
      )
    }

    findView<Button>(R.id.btn_parent_access)
        .value.setOnClickListener{
      LoginActivity.start(
          this@NavigationActivity, USER_PARENT
      )
    }
  }
}