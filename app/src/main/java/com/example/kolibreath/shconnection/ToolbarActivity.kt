package com.example.kolibreath.shconnection

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.TextView
import com.example.kolibreath.shconnection.extensions.findView
import org.jetbrains.anko.find
import org.jetbrains.anko.toolbar

open class ToolbarActivity : BaseActivity() {

   private val mToolbar: Toolbar by findView(R.id.toolbar)

  override fun setContentView(layoutResID: Int) {
    super.setContentView(layoutResID)
    initToolbar()
  }

  private fun canBack():Boolean {
    return true;
  }
  private fun initToolbar(){
    setSupportActionBar(mToolbar)
    mToolbar.setTitle("")
    if(canBack()){
      val actionBar = supportActionBar
      if(actionBar != null){
        actionBar.setDisplayHomeAsUpEnabled(true)
      }
    }
  }

  private fun setTitle(title:String){
    mToolbar.setTitle("")
      val textView:TextView = mToolbar.find(R.id.tv_title)
    textView.setText(title)
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
