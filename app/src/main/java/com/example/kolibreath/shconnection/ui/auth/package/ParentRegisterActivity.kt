package com.example.kolibreath.shconnection.ui.auth.`package`

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toolbar
import com.example.kolibreath.shconnection.base.ui.ToolbarActivity

class ParentRegisterActivity : ToolbarActivity(){

  companion object {
    fun start(context:Context){
      val intent = Intent(context,this::class.java)
      context.startActivity(intent)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }
}