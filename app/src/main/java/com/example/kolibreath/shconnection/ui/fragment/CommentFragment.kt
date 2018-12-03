package com.example.kolibreath.shconnection.ui.main.fragment

import LOGIN_TOKEN
import USER_PARENT
import USER_TEACHER
import USER_TYPE
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.ExpandableListView
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.adapter.AddressAdapter
import com.example.kolibreath.shconnection.adapter.CommentsAdapter
import com.example.kolibreath.shconnection.base.AddressBean
import com.example.kolibreath.shconnection.base.ParentList
import com.example.kolibreath.shconnection.base.Person
import com.example.kolibreath.shconnection.base.TeacherList
import com.example.kolibreath.shconnection.base.net.NetFactory
import com.example.kolibreath.shconnection.base.ui.BaseFragment
import org.jetbrains.anko.support.v4.find
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * 评价fragment
 */
class CommentFragment: BaseFragment() , View.OnClickListener {

    lateinit var elv: ExpandableListView
    lateinit var mAdapter: AddressAdapter
    lateinit var mList: MutableList<String>
    lateinit var mComment: MutableList<MutableList<Person>>
//    lateinit var

    override fun getLayoutResources(): Int {
        return R.layout.fragment_comment
    }

    override fun initView() {
        elv = find(R.id.elv_comment) as ExpandableListView

        initData()
        mAdapter = AddressAdapter(context!!,mList,mComment)
    }

    override fun initData(){
        when (USER_TYPE){
            USER_PARENT.toString() ->
                NetFactory.retrofitService.parentList(token = LOGIN_TOKEN)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : Subscriber<ParentList>() {
                            override fun onNext(t: ParentList?) {

                            }
                            override fun onCompleted() { }
                            override fun onError(e: Throwable?) { e?.printStackTrace() }
                        })
            USER_TEACHER.toString() ->
                NetFactory.retrofitService.teacherList(token = LOGIN_TOKEN)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : Subscriber<TeacherList>() {
                            override fun onNext(t: TeacherList?) {

                            }
                            override fun onCompleted() { }
                            override fun onError(e: Throwable?) { e?.printStackTrace() }
                        })
        }

    }

    override fun onClick(v: View?) {


    }

}