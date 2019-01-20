package com.example.kolibreath.shconnection.ui.main.fragment

import LOGIN_TOKEN
import android.content.Intent
import android.widget.ExpandableListView
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.adapter.AddressAdapter
import com.example.kolibreath.shconnection.base.AddressBean
import com.example.kolibreath.shconnection.base.Person
import com.example.kolibreath.shconnection.base.net.NetFactory
import com.example.kolibreath.shconnection.base.ui.BaseFragment
import com.example.kolibreath.shconnection.ui.UserProfile
import org.jetbrains.anko.support.v4.find
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * 通讯录界面
 */

class AddressFragment: BaseFragment(){

    var mList = ArrayList<String>()
    var mAddress : MutableList<List<Person>> = ArrayList()
    var mAdapter : AddressAdapter? = null
    var elv: ExpandableListView? = null
    lateinit var mAddressBean: AddressBean
    var click: Int = -1

    override fun getLayoutResources(): Int {
        return R.layout.fragment_address
    }

    override fun initView() {
        elv = find(R.id.elv_address)

        initData()

        mList.add("老师")
        mList.add("家长")
        mAddress.add(mAddressBean.getTeacher()!!)
        mAddress.add(mAddressBean.getParent()!!)
        mAdapter = AddressAdapter(context!!, mList, mAddress)
        val intent = Intent()
        elv?.setOnGroupClickListener { parent, v, groupPosition, id ->
            TODO("Group点击事件，点击一个Group隐藏其他的(只显示一个)")
        }
//        子项点击事件
        elv?.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            TODO("点击进入UserProfile")
        }
    }

    override fun initData(){
        NetFactory.retrofitService.classAddress(token = LOGIN_TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<AddressBean>() {
                    override fun onNext(t: AddressBean?) {
                        mAddressBean = t!!
                    }
                    override fun onCompleted() { }
                    override fun onError(e: Throwable?) { e?.printStackTrace() }
                })
    }



}