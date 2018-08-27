package com.example.kolibreath.shconnection.extensions

import REQUEST_CODE_IMAGE_ALBUM
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.view.View
import android.webkit.WebView.HitTestResult.IMAGE_TYPE
import com.example.kolibreath.shconnection.R.color

fun Activity.showSnackBarLong(msg:String) {
  Snackbar.make(window.decorView,msg, Snackbar.LENGTH_LONG).show()
}

fun Activity.showSnackBarShort(msg:String){
  Snackbar.make(window.decorView,msg, Snackbar.LENGTH_SHORT).show()
}

fun Activity.showSnackBarShort(msg:String,listener:(view: View)->Unit){
  Snackbar.make(window.decorView,msg, Snackbar.LENGTH_SHORT).setAction("确定",listener).show()
}


fun Activity.showSnackBarLong(msg:String,listener:(view: View)->Unit){
  Snackbar.make(window.decorView,msg, Snackbar.LENGTH_LONG).setAction("确定",listener).show()
}

fun Activity.showErrorSnackbarShort(msg: String) {
  val snackbar: Snackbar = Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).apply{
    setText(msg)
  }
  val view = snackbar.view
  view.setBackgroundColor(resources.getColor(color.red))
  snackbar.show()
}

fun Activity.showErrorSnackbarShort(msg: String,listener: (view: View) -> Unit) {
  val snackbar: Snackbar = Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).apply{
    setText(msg)
  }
  val view = snackbar.view
  view.setBackgroundColor(resources.getColor(color.red))
  snackbar.setAction("确定",listener)
  snackbar.show()
}



fun Activity.openAlbum(){
  val IMAGE_TYPE = "image/*";
  val intent  = Intent();
  intent.addCategory(Intent.CATEGORY_OPENABLE)
  intent.type = IMAGE_TYPE
  if(Build.VERSION.SDK_INT < 19)
    intent.action = Intent.ACTION_GET_CONTENT
  else
    intent.action = Intent.ACTION_OPEN_DOCUMENT

  startActivityForResult(intent,REQUEST_CODE_IMAGE_ALBUM)
}