package com.example.kolibreath.shconnection.base.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.extensions.createView

class CancelOrOkDialog(context:Context, title:String,
    confirmListener:()->Unit,cancelListener:()->Unit)
  : Dialog(context,R.style.custom_dialog){


  init {
    setContentView(R.layout.view_cancel_or_ok_dialog)
    setCancelable(false)

    val textView : TextView = findViewById(R.id.tv_title)
    textView.text = title

    findViewById<TextView>(R.id.btn_confirm).setOnClickListener{ confirmListener.invoke() }
    findViewById<TextView>(R.id.btn_cancel).setOnClickListener{
      cancel()
      cancelListener.invoke()}
  }
}