package com.example.kolibreath.shconnection.base.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment : Fragment() {

    var isFragmentVisible = false
    var rootView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView==null){
            rootView = inflater!!.inflate(getLayoutResources(),container,false)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser){
            isFragmentVisible = true
        }
        if (rootView == null){
            return
        }
        if (!isFragmentVisible){
            onFragmentVisibleChange(true)
        }
        if (isFragmentVisible){
            onFragmentVisibleChange(false)
            isFragmentVisible = false
        }
    }

    open protected fun onFragmentVisibleChange(b: Boolean){

    }

    abstract fun getLayoutResources(): Int

    abstract fun initView()

    abstract fun initData()
}