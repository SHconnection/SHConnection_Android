package com.example.kolibreath.shconnection.base.ui

import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import com.example.kolibreath.shconnection.R.id
import com.example.kolibreath.shconnection.extensions.findView
import org.jetbrains.anko.find

open class ToolbarActivity : BaseActivity() {

  private val mToolbar: Toolbar by findView(id.toolbar)
  open fun canBack():Boolean  = true


  override fun setContentView(layoutResID: Int) {
    super.setContentView(layoutResID)
    initToolbar()
  }


  private fun initToolbar(){
    setSupportActionBar(mToolbar)
    if(mToolbar == null)
      Log.d("fuck","fuck")
    mToolbar.title = ""
    if(canBack()){
      val actionBar = supportActionBar
      actionBar?.setDisplayHomeAsUpEnabled(true)
    }
  }

  private fun setTitle(title:String){
    mToolbar.title = ""
      val textView:TextView = mToolbar.find(id.tv_title)
    textView.text = title
    setSupportActionBar(mToolbar)

  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when(item.itemId){
      android.R.id.home ->{
        onBackPressed()
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onBackPressed() {
    if(supportFragmentManager.backStackEntryCount > 0)
      supportFragmentManager.popBackStack()
    else
      super.onBackPressed()
  }
}
