package com.example.kolibreath.shconnection.ui.main.fragment

import LOGIN_TOKEN
import USER_PARENT
import USER_TEACHER
import USER_TYPE
import android.view.View
import android.widget.ExpandableListView
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.adapter.AddressAdapter
import com.example.kolibreath.shconnection.base.AddressBean
import com.example.kolibreath.shconnection.base.Person
import com.example.kolibreath.shconnection.base.net.NetFactory
import com.example.kolibreath.shconnection.base.ui.BaseFragment
import org.jetbrains.anko.support.v4.find
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * 通讯录界面
 */

class AddressFragment: BaseFragment(), View.OnClickListener {

    var mList = ArrayList<String>()
    var mAddress : MutableList<MutableList<String>> = ArrayList()
    var mAdapter : AddressAdapter? = null
    var elv: ExpandableListView? = null
    lateinit var mTeacher : MutableList<String>
    lateinit var mParent : MutableList<String>
    lateinit var mAddressBean: AddressBean

    override fun getLayoutResources(): Int {
        return R.layout.fragment_address
    }

    override fun initView() {
        elv = find(R.id.elv_address)
        initData()
        mList.add("老师")
        mList.add("家长")
        for (item : Person in mAddressBean.getTeacher()!!.iterator()){
            mTeacher.add(item.name!!)
        }
        for (item in mAddressBean.getParent()!!.iterator()){
            mParent.add(item.name!!)
        }
        mAddress.add(mTeacher)
        mAddress.add(mParent)

        mAdapter = AddressAdapter(context!!, mList, mAddress)
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

    override fun onClick(v: View?) {

    }
}