package com.example.kolibreath.shconnection.ui.auth

import REQUEST_CODE_IMAGE_ALBUM
import com.example.kolibreath.shconnection.extensions.REQUEST_CODE
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.net.NetFactory
import com.example.kolibreath.shconnection.base.ui.ToolbarActivity
import com.example.kolibreath.shconnection.extensions.findView
import com.example.kolibreath.shconnection.extensions.showErrorSnackbarShort
import com.example.kolibreath.shconnection.extensions.showSnackBarShort
import com.uuzuche.lib_zxing.activity.CodeUtils.AnalyzeCallback
import com.uuzuche.lib_zxing.activity.CodeUtils.analyzeBitmap
import com.example.kolibreath.shconnection.extensions.isGranted
import com.example.kolibreath.shconnection.extensions.openAlbum
import com.example.kolibreath.shconnection.extensions.requestPermissions
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.schedulers.Schedulers.test
import testing

class LoginActivity : ToolbarActivity(){

  private var mPermissionGranted = false;

  private val mEdvUsername  by findView< EditText>(R.id.tv_username)
  private val mEdvUserpassword  by findView<EditText>(R.id.tv_password)
  private val mBtnConfirm  by findView<Button>(R.id.btn_confirm).apply {
    this.value.setOnClickListener {
      login()
    }
  }
  private val mBtnForgetPassword by findView<TextView>(R.id.tv_forget_password).apply {
    this.value.setOnClickListener {
      //todo add forget password interface
    }
  }

  private val mBtnTeacherLogin by findView<EditText>(R.id.tv_teacher_login).apply {
    this.value.setOnClickListener{
      //todo add teacher login
    }
  }

  override fun canBack(): Boolean  = false

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_scan,menu)
    return true;
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    val id = item.itemId
    if(mPermissionGranted) {
      when (id) {
        R.id.action_scan_from_actual_view -> {
          val intent = Intent(this, ScanActivity::class.java)
          startActivity(intent)
        }
        R.id.action_scan_from_system_gallery ->{
          openAlbum()
        }
      }
    }else{
      showErrorSnackbarShort("没有赋予照相权限")
    }
    return false
  }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_auth_login)

    requestPermissions(this)
    test()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if(Activity.RESULT_OK != requestCode)
      return;
    when(requestCode){
      REQUEST_CODE_IMAGE_ALBUM -> {
        val uri:Uri? = data?.data;
        if(uri == null){
          showErrorSnackbarShort("图片路径错误")
          //return to the caller
          return
        }
        //todo test getEncodedPath if ok
        analyzeBitmap(uri.encodedPath, object: AnalyzeCallback{
          override fun onAnalyzeSuccess(mBitmap: Bitmap?, result: String?) {
            //todo process the result
            showSnackBarShort("解析成功"){
              //todo listener
            }
          }

          override fun onAnalyzeFailed() {
            showErrorSnackbarShort("解析失败"){

            }
          }
        })
      }
    }
  }

  override fun onRequestPermissionsResult(
    requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
       when(requestCode){
         REQUEST_CODE ->{
           if(isGranted(
                   grantedResult = grantResults
               )
           ){
             Log.d("SHConnection","请求成功")
           }else{
             mPermissionGranted = true
           }
         }
       }
  }



  private fun login(){
    //todo currently not testing the login button
    if(testing) return

    val userName = mEdvUsername.text.toString()
    val userPassword  = mEdvUserpassword.text.toString()

    if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPassword))
      return

    NetFactory.retrofitService
        .login(userName =  userName, userPassword = userPassword)
        .subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(Schedulers.io())
        .subscribe(object:Subscriber<Any>(){
          override fun onNext(t: Any?) {
            //todo
          }

          override fun onCompleted() {
          }

          override fun onError(e: Throwable?) {
          }
        })

  }

  }