package com.example.kolibreath.shconnection.base.ui

import CLASS_ID
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.R.id
import com.example.kolibreath.shconnection.R.id.action_scan_from_actual_view
import com.example.kolibreath.shconnection.R.id.action_scan_from_system_gallery
import com.example.kolibreath.shconnection.base.RxBus
import com.example.kolibreath.shconnection.base.ScanEvent
import com.example.kolibreath.shconnection.extensions.Preference
import com.example.kolibreath.shconnection.extensions.decode
import com.example.kolibreath.shconnection.ui.scan.ScanActivity
import org.jetbrains.anko.find
import rx.Subscriber
import rx.Subscription

open class ToolbarActivity : BaseActivity() {

  private lateinit var mSubscription: Subscription
  private var mClassId : String by Preference(name = CLASS_ID, default = "")

  private lateinit var mToolbar: Toolbar

  private lateinit var mActionBar : ActionBar

  override fun setContentView(layoutResID: Int) {
    super.setContentView(layoutResID)
    initToolbar()
  }


  private fun initToolbar(){

    mToolbar = findViewById<Toolbar>(R.id.toolbar)
    setSupportActionBar(mToolbar)

    mToolbar.title = ""

    mActionBar = supportActionBar!!
    enableBack()
  }

  protected fun enableBack(boolean: Boolean = true) = mActionBar.setDisplayHomeAsUpEnabled(boolean)

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
      action_scan_from_actual_view or   action_scan_from_system_gallery  ->{
        ScanActivity.start(this)
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_scan,menu)
    return true
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    //listen to the scan result
    mSubscription = RxBus.getDefault().toObservable(ScanEvent::class.java)
        .subscribe(object:Subscriber<ScanEvent>(){
          @RequiresApi(VERSION_CODES.O)
          override fun onNext(t: ScanEvent?) {mClassId = this@ToolbarActivity.decode(t!!.classId!!)}
          override fun onCompleted() {}
          override fun onError(e: Throwable?) {e?.printStackTrace()} })
  }

  override fun onDestroy() {
    mSubscription.unsubscribe()
    super.onDestroy()
  }
  override fun onBackPressed() {
    if(supportFragmentManager.backStackEntryCount > 0)
      supportFragmentManager.popBackStack()
    else
      super.onBackPressed()
  }
}
