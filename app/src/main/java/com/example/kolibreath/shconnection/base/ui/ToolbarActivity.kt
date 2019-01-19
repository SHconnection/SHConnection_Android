package com.example.kolibreath.shconnection.base.ui

<<<<<<< HEAD
import android.os.Bundle
=======
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.support.annotation.RequiresApi
>>>>>>> kolibreath
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.R.id
<<<<<<< HEAD
=======
import com.example.kolibreath.shconnection.base.RxBus
import com.example.kolibreath.shconnection.base.ScanEvent
import com.example.kolibreath.shconnection.extensions.decode
>>>>>>> kolibreath
import org.jetbrains.anko.find
import rx.Subscriber
import rx.Subscription

open class ToolbarActivity : BaseActivity() {

  private lateinit var mToolbar: Toolbar

  private lateinit var mActionBar : ActionBar

  override fun setContentView(layoutResID: Int) {
    super.setContentView(layoutResID)
    initToolbar()
  }


  private fun initToolbar(){

<<<<<<< HEAD
    mToolbar = find(R.id.toolbar) as Toolbar
=======
    mToolbar = findViewById<Toolbar>(R.id.toolbar)
>>>>>>> kolibreath
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

    }
    return super.onOptionsItemSelected(item)
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_scan,menu)
    return true
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onDestroy() {
    super.onDestroy()
  }
  override fun onBackPressed() {
    if(supportFragmentManager.backStackEntryCount > 0)
      supportFragmentManager.popBackStack()
    else
      super.onBackPressed()
  }
}
