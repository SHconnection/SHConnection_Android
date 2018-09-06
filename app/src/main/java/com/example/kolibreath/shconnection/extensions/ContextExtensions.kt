package com.example.kolibreath.shconnection.extensions

import android.app.Activity
import android.support.design.widget.Snackbar
import android.view.View
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