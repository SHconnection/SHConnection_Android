package com.example.kolibreath.shconnection.ui.main

import ID
import USER_TYPE
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
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
import com.example.kolibreath.shconnection.extensions.replaceFragment
import com.example.kolibreath.shconnection.ui.main.fragment.AddressFragment
import com.example.kolibreath.shconnection.ui.main.fragment.CommentFragment
import com.example.kolibreath.shconnection.ui.main.fragment.HomeFragment
import com.example.kolibreath.shconnection.ui.main.profile.UserProfileActivity
import java.util.*

class MainActivity : AppCompatActivity(), BottomNavigationBar.OnTabSelectedListener {
    private var mBtmBar: BottomNavigationBar? = null
    private var mViewPager: ViewPager? = null
    private var mAdapter: FragmentAdapter? = null
    private val mItemList = ArrayList<BottomNavigationItem>()
    private val mFragments = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFragment()
        initView()
    }

    private fun initView() {

        mBtmBar = findViewById(R.id.bottom_navigation_bar)
        mBtmBar!!.setTabSelectedListener(this)

        initBottomNavigationItem()

        mBtmBar!!.setMode(BottomNavigationBar.MODE_FIXED)
        mBtmBar!!.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE)
        //todo to change the main color of it
        mBtmBar!!.backgroundColor = Color.BLACK
        mBtmBar!!.addItem(mItemList[0])
                .addItem(mItemList[1])
                .addItem(mItemList[2])
                .setFirstSelectedPosition(0)
                .initialise()

//        mBtmBar!!.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener{
//            override fun onTabReselected(position: Int) {
////                when(position){
//////                    0 ->{
//////                        index = 0;
//////                        replaceFragment(mFragments[index],R.id.viewpager)
//////                    }
//////                    1 ->{
//////                        index = 1
//////                        replaceFragment(mFragments[index],R.id.viewpager)
//////                    }
//////                    2 ->{
//////                        index = 2
//////                        replaceFragment(mFragments[index],R.id.viewpager)
//////                    }
//////                }
//            }
//
//            override fun onTabUnselected(position: Int) {}
//            override fun onTabSelected(position: Int) {
//
//            }
//        })

        val mBtnProfile = findViewById<ImageView>(R.id.btn_profile)
        mBtnProfile.setOnClickListener {
            UserProfileActivity.start(this@MainActivity, getValue(ID,-1), getValue(USER_TYPE,-1))
        }

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
        when(position){
            0 ->{
                index = 0
                replaceFragment(mFragments[index],R.id.viewpager)
            }
            1 ->{
                index = 1
                replaceFragment(mFragments[index],R.id.viewpager)
            }
            2 ->{
                index = 2
                replaceFragment(mFragments[index],R.id.viewpager)
            }
        }
    }

    override fun onTabUnselected(position: Int) {

    }

    override fun onTabReselected(position: Int) {

    }

    private fun initFragment() {
        mViewPager = findViewById(R.id.viewpager)
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

        private var index: Int = 0

        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}
