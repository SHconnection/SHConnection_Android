package com.example.kolibreath.shconnection.ui.auth.`package`

import LOGIN_TOKEN
import REQUEST_CODE_IMAGE_ALBUM
import android.app.Activity
import android.content.Context
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
import com.example.kolibreath.shconnection.base.LoginBody
import com.example.kolibreath.shconnection.base.LoginToken
import com.example.kolibreath.shconnection.base.net.NetFactory
import com.example.kolibreath.shconnection.base.ui.ToolbarActivity
import com.example.kolibreath.shconnection.extensions.Preference
import com.example.kolibreath.shconnection.extensions.REQUEST_CODE
import com.example.kolibreath.shconnection.extensions.findView
import com.example.kolibreath.shconnection.extensions.isGranted
import com.example.kolibreath.shconnection.extensions.logger
import com.example.kolibreath.shconnection.extensions.openAlbum
import com.example.kolibreath.shconnection.extensions.showErrorSnackbarShort
import com.example.kolibreath.shconnection.extensions.showSnackBarShort
import com.example.kolibreath.shconnection.ui.auth.ScanActivity
import com.uuzuche.lib_zxing.activity.CodeUtils
import com.uuzuche.lib_zxing.activity.CodeUtils.AnalyzeCallback
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import testing

class ParentLoginActivity:ToolbarActivity(){

  private var mPermissionGranted = false;

  private val mEdvUsername  by findView<EditText>(R.id.tv_username)
  private val mEdvUserpassword  by findView<EditText>(R.id.tv_password)

  private val mBtnConfirm  by findView<Button>(R.id.btn_confirm)
  private val mBtnForgetPassword by findView<TextView>(R.id.tv_forget_password)

  override fun canBack(): Boolean  = false

  companion object {
    fun start(context: Context){
      context.startActivity(Intent(context,this::class.java))
    }
  }

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
          openAlbum {
            if(Build.VERSION.SDK_INT < 19)
              intent.action = Intent.ACTION_GET_CONTENT
            else
              intent.action = Intent.ACTION_OPEN_DOCUMENT

            startActivityForResult(it,REQUEST_CODE_IMAGE_ALBUM)
          }
        }
      }
    }else{
      showErrorSnackbarShort("没有赋予照相权限")
    }
    return false
  }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_auth_parent_login)

    com.example.kolibreath.shconnection.extensions.requestPermissions(this)
    Schedulers.test()

    initView()
  }

  private fun initView(){
    mBtnConfirm.setOnClickListener {
      login()
    }

    mBtnForgetPassword.setOnClickListener {
      //todo add forget password interface
    }

  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if(Activity.RESULT_OK != requestCode)
      return;
    when(requestCode){
      REQUEST_CODE_IMAGE_ALBUM -> {
        val uri: Uri? = data?.data;
        if(uri == null){
          showErrorSnackbarShort("图片路径错误")
          //return to the caller
          return
        }
        //todo test getEncodedPath if ok
        CodeUtils.analyzeBitmap(uri.encodedPath, object : AnalyzeCallback {
          override fun onAnalyzeSuccess(
            mBitmap: Bitmap?,
            result: String?
          ) {
            //todo process the result
            showSnackBarShort("解析成功") {
              //todo listener
            }
          }

          override fun onAnalyzeFailed() {
            showErrorSnackbarShort("解析失败") {

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
    //todo currently not testing the teacherLogin button
    if(testing) return

    val userName = mEdvUsername.text.toString()
    val userPassword  = mEdvUserpassword.text.toString()

    if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPassword))
      return

    val teacherLoginBody =
      LoginBody(userName, userPassword)
    NetFactory.retrofitService
        .teacherLogin(teacherLoginBody)
        .subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(Schedulers.io())
        .subscribe(object: Subscriber<LoginToken>(){
          override fun onNext(t: LoginToken?) {
            Preference(LOGIN_TOKEN,"")
          }

          override fun onCompleted() {
            logger("login complete")
          }

          override fun onError(e: Throwable?) {
            e?.printStackTrace()
          }
        })

  }

}