package com.example.kolibreath.shconnection.extensions

import android.app.Activity
import android.content.Context
import android.support.annotation.IdRes
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View

/**
 * layoutInflater extension
 */

fun LayoutInflater.createView(resId:Int,context:Context):View{
  return LayoutInflater.from(context).inflate(resId,null,false)
}

fun <T: View> Activity.findView(@IdRes res:Int): Lazy<T> {

  @Suppress("UNCHECKED_CAST")
  return lazy {
    this.findViewById<T>(res)
  }
}