package com.example.kolibreath.shconnection

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.text.AlteredCharSequence.make
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import rx.Subscription
import rx.subscriptions.CompositeSubscription

open class BaseActivity : AppCompatActivity() {

  private val mMenu: Menu? = null
  private var mActionBar: ActionBar? = null

  private var mCompositeSubscription: CompositeSubscription? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mActionBar = supportActionBar
    if (mActionBar != null)
      mActionBar!!.setDisplayHomeAsUpEnabled(true)

    setStatusBar(Color.WHITE)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    val itemId = item.itemId
    when (itemId) {
      android.R.id.home -> onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }

  //防止内存泄漏
  override fun onDestroy() {
    super.onDestroy()
    if (mCompositeSubscription != null) {
      mCompositeSubscription!!.unsubscribe()
    }
  }

  fun addSubscription(s: Subscription) {
    if (mCompositeSubscription != null) {
      mCompositeSubscription = CompositeSubscription()
    }
    mCompositeSubscription!!.add(s)
  }

  private fun setStatusBar(color: Int) {
    window.decorView.setBackgroundColor(color)
  }

  override fun getLayoutInflater(): LayoutInflater {
    return LayoutInflater.from(this)
  }

  fun showSnackBarLong(msg:String) {
    Snackbar.make(window.decorView,msg,Snackbar.LENGTH_LONG).show()
  }

  fun showSnackBarShort(msg:String){
    Snackbar.make(window.decorView,msg,Snackbar.LENGTH_SHORT).show()
  }

  fun showSnackBarShort(msg:String,listener:(view:View)->Unit){
    Snackbar.make(window.decorView,msg,Snackbar.LENGTH_SHORT).setAction("确定",listener).show()
  }


  fun showSnackBarLong(msg:String,listener:(view:View)->Unit){
    Snackbar.make(window.decorView,msg,Snackbar.LENGTH_LONG).setAction("确定",listener).show()
  }

  fun showErrorSnackbarShort(msg: String) {
    val snackbar: Snackbar = Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).apply{
      setText(msg)
    }
    val view = snackbar.view
    view.setBackgroundColor(resources.getColor(R.color.red))
    snackbar.show()
  }

  fun showErrorSnackbarShort(msg: String,listener: (view: View) -> Unit) {
    val snackbar: Snackbar = Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).apply{
      setText(msg)
    }
    val view = snackbar.view
    view.setBackgroundColor(resources.getColor(R.color.red))
    snackbar.setAction("确定",listener)
    snackbar.show()
  }
}
