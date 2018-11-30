package com.example.kolibreath.shconnection.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class FragmentAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {
    var mFragments = ArrayList<Fragment>()

    fun addFragment(fragmentList: List<Fragment>){
        for (fragment in fragmentList) {
            mFragments.add(fragment)
        }
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }
}