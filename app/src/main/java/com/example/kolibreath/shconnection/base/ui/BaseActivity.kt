package com.example.kolibreath.shconnection.base.ui

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.kolibreath.shconnection.R.color
import org.slf4j.LoggerFactory
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


}
