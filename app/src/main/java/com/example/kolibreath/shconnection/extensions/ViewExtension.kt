package com.example.kolibreath.shconnection.extensions

import android.app.Activity
import android.content.Context
import android.os.Build.VERSION_CODES
import android.support.annotation.IdRes
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView


fun <T: View> Activity.findView(@IdRes res:Int): Lazy<T> {

  @Suppress("UNCHECKED_CAST")
  return lazy {
    this.findViewById<T>(res)
  }
}

@RequiresApi(VERSION_CODES.M)
fun View.setBgColor(id:Int){
  val context = context
  this.setBackgroundColor(context.getColor(id))
}

fun TextView.setTxColor(id:Int){
  val context = context
  setTextColor(context.getColor(id))
}