package com.example.kolibreath.shconnection.ui.main.fragment


import CLASS_ID
import LOGIN_TOKEN
import android.widget.ExpandableListView
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.adapter.AddressAdapter
import com.example.kolibreath.shconnection.base.ClassAddress
import com.example.kolibreath.shconnection.base.People
import com.example.kolibreath.shconnection.base.net.NetFactory
import com.example.kolibreath.shconnection.base.ui.BaseFragment
import com.example.kolibreath.shconnection.extensions.getValue
import org.jetbrains.anko.support.v4.find
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * 通讯录界面
 */

class AddressFragment: BaseFragment(){

    var mList = ArrayList<String>()
    var mAddress : MutableList<List<People>> = ArrayList()
    var mAdapter : AddressAdapter? = null
    var elv: ExpandableListView? = null
    lateinit var mAddressBean: ClassAddress

    override fun getLayoutResources(): Int {
        return R.layout.fragment_address
    }

    override fun initView() {
        elv = find(R.id.elv_address) as ExpandableListView
        initData()
    }

    override fun initData(){
        mList.clear()
        mAddress.clear()
        mList.add("家长")
        mList.add("老师")
        NetFactory.retrofitService.classAddress(token = getValue(LOGIN_TOKEN,""),cid = getValue(CLASS_ID,"").toInt())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<ClassAddress>() {
                    override fun onNext(t: ClassAddress?) {
                        mAddressBean = t!!
                        mAddress.add(mAddressBean.parent)
                        mAddress.add(mAddressBean.teacher)
                        mAdapter = AddressAdapter(context!!, mList, mAddress)
                        elv!!.setAdapter(mAdapter)

                    }
                    override fun onCompleted() { }
                    override fun onError(e: Throwable?) { e?.printStackTrace() }
                })
    }



}