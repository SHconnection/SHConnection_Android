package com.example.kolibreath.shconnection.extensions

import REQUEST_CODE_IMAGE_ALBUM
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Environment
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.webkit.WebView.HitTestResult.IMAGE_TYPE
import com.example.kolibreath.shconnection.R.color
import org.jetbrains.anko.ctx
import org.jetbrains.anko.layoutInflater
import java.io.File
import java.lang.Exception

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

/**
 * 启动系统相簿
 */
fun Context.openAlbum(action:(intent:Intent)->Unit){
  val IMAGE_TYPE = "image/*";
  val intent  = Intent();
  intent.addCategory(Intent.CATEGORY_OPENABLE)
  intent.type = IMAGE_TYPE
  action(intent)
}

/**
 * 启动系统照相机
 */

fun Context.openCamera(action: (intent:Intent) -> Unit){
  val intent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
  if(intent.resolveActivity(packageManager) != null){
    val imageUri = Uri.fromFile(getTempFile())
    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri)
    action()
  }
}

private fun getTempFile(): File? {
  if (android.os.Environment.getExternalStorageState() == android.os.Environment.MEDIA_MOUNTED) {
    val tempFile = File(Environment.getExternalStorageDirectory(), "temp.jpg")
    try {
      tempFile.createNewFile()
    }catch (e:Exception){
      e.printStackTrace()
    }
    return tempFile
  }
  return  null
}

fun Context.getScaledBitmp(filePath:String): Bitmap {
  val ctx  = this
  val opt = BitmapFactory.Options()
  opt.inJustDecodeBounds = true
  var bitmap = BitmapFactory.decodeFile(filePath,opt)

  val bmpWidth = opt.outWidth;
  val bmpHeight = opt.outHeight

  val windowManager:WindowManager = ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager
  val display = windowManager.defaultDisplay

  val screenWidth = display.width
  val screenHeight = display.height

  opt.inSampleSize = 1
  if(bmpWidth > bmpHeight)
    if(bmpWidth > screenHeight)
      opt.inSampleSize = bmpWidth / screenWidth
  else
      if(bmpHeight > screenHeight)
        opt.inSampleSize = bmpHeight / screenHeight

  opt.inJustDecodeBounds = false

  bitmap = BitmapFactory.decodeFile(filePath,opt)
  return bitmap
}


/**
 * layoutInflater extension
 */

fun Context.createView(resId:Int):View{
  return LayoutInflater.from(this).inflate(resId,null,false)
}

