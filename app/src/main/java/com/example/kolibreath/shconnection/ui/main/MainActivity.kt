package com.example.kolibreath.shconnection.ui.main

import ID
import USER_TYPE
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.ashokvarma.bottomnavigation.TextBadgeItem
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.adapter.FragmentAdapter
import com.example.kolibreath.shconnection.extensions.getValue
import com.example.kolibreath.shconnection.ui.main.fragment.AddressFragment
import com.example.kolibreath.shconnection.ui.main.fragment.CommentFragment
import com.example.kolibreath.shconnection.ui.main.fragment.HomeFragment
import com.example.kolibreath.shconnection.ui.main.news.ViewPictureActivity
import com.example.kolibreath.shconnection.ui.main.profile.UserProfileActivity
import java.util.*

class MainActivity : AppCompatActivity(), BottomNavigationBar.OnTabSelectedListener {
    private var mFab: FloatingActionButton? = null
    private var mBtmBar: BottomNavigationBar? = null
    private var mViewPager: ViewPager? = null
    private var mAdapter: FragmentAdapter? = null
    private val mItemList = ArrayList<BottomNavigationItem>()
    private val mFragments = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initFragment()
    }

    private fun initView() {

        mFab = findViewById(R.id.fab)
        mBtmBar = findViewById(R.id.bottom_navigation_bar)
        mViewPager = findViewById(R.id.viewpager)

        mBtmBar!!.setTabSelectedListener(this)

        initBottomNavigationItem()

        mBtmBar!!.setMode(BottomNavigationBar.MODE_FIXED)
        mBtmBar!!.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE)
        //todo to change the main color of it
        mBtmBar!!.backgroundColor = Color.BLACK
        mBtmBar!!.addItem(mItemList[0]).addItem(mItemList[1]).addItem(mItemList[2])
                .setFirstSelectedPosition(1)
                .initialise()
        //todo 这个需要作为页面切换
        //    mBtmBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
        //      @Override
        //      public void onTabSelected(int position) {
        //        switch (position){
        //          case 0:
        //            index = 0;
        //            replaceFragment(MainActivity.this,mFragments.get(index),R.id.viewpager);
        //            break;
        //          case 1:
        //            index = 1;
        //            replaceFragment(MainActivity.this,mFragments.get(index),R.id.viewpager);
        //            break;
        //          case 2:
        //            index = 2;
        //            replaceFragment(MainActivity.this,mFragments.get(index),R.id.viewpager);
        //
        //        }
        //      }
        //
        //      @Override
        //      public void onTabUnselected(int position) {
        //
        //      }
        //
        //      @Override
        //      public void onTabReselected(int position) {
        //
        //      }
        //    });

        val mBtnProfile = findViewById<ImageView>(R.id.btn_profile)
        mBtnProfile.setOnClickListener {
            UserProfileActivity.start(this@MainActivity, getValue(ID,-1), getValue(USER_TYPE,-1))
        }

        mFab!!.setOnClickListener { ViewPictureActivity.start(this@MainActivity) }
    }

    //todo set it for the new navigation item
    private fun initBottomNavigationItem() {

        val item1 = BottomNavigationItem(R.drawable.ic_address, "通讯录")
        val item2 = BottomNavigationItem(R.drawable.ic_home, "家校圈")
        val item3 = BottomNavigationItem(R.drawable.ic_comment, "孩子评价")

        mItemList.add(item1)
        mItemList.add(item2)
        mItemList.add(item3)
    }

    private fun setBottomBadge(index: Int, text: String) {
        val item = TextBadgeItem().setBackgroundColor(Color.RED)
                .setText(text)
        mItemList[index].setBadgeItem(item)
    }

    override fun onTabSelected(position: Int) {

    }

    override fun onTabUnselected(position: Int) {

    }

    override fun onTabReselected(position: Int) {

    }

    private fun initFragment() {
        val addressFragment = AddressFragment()
        val homeFragment = HomeFragment()
        val commentFragment = CommentFragment()
        val manager = supportFragmentManager
        mAdapter = FragmentAdapter(manager)
        mFragments.add(addressFragment)
        mFragments.add(homeFragment)
        mFragments.add(commentFragment)
        mAdapter!!.addFragment(mFragments)
        mViewPager!!.adapter = mAdapter
    }

    companion object {

        private val index: Int = 0

        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}
