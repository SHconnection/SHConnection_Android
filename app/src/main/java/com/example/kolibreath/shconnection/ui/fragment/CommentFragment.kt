package com.example.kolibreath.shconnection.ui.main.fragment

import LOGIN_TOKEN
import USER_PARENT
import USER_TEACHER
import USER_TYPE
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.adapter.CommentAdapter
import com.example.kolibreath.shconnection.base.CommentList
import com.example.kolibreath.shconnection.base.net.NetFactory
import com.example.kolibreath.shconnection.base.ui.BaseFragment
import com.example.kolibreath.shconnection.extensions.getValue
import com.example.kolibreath.shconnection.ui.SendCommentActivity
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.support.v4.find
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.logging.Logger

/**
 * 评价fragment
 */
class CommentFragment: BaseFragment() , View.OnClickListener {

    lateinit var recyclerView: RecyclerView
    lateinit var mAdapter: CommentAdapter
    lateinit var mButton:FloatingActionButton

    override fun getLayoutResources(): Int {
        return R.layout.fragment_comment
    }

    override fun initView() {

        recyclerView = find(R.id.eval_rv) as RecyclerView
        mButton = find(R.id.send_comment_btn) as FloatingActionButton
        recyclerView.layoutManager = LinearLayoutManager(context)
        initData()
        mButton.setOnClickListener(this)
    }

    override fun initData() {
        when(getValue(USER_TYPE,-1)){
            USER_TEACHER ->
                NetFactory.retrofitService.teacherList(token = getValue(LOGIN_TOKEN,""))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : Subscriber<List<CommentList>>() {
                            override fun onNext(t: List<CommentList>?) {
                                mAdapter = CommentAdapter(t!!)
                                recyclerView.adapter = mAdapter
                            }
                            override fun onCompleted() {}
                            override fun onError(e: Throwable?) {
                                e?.printStackTrace()
                            }
                        })
            USER_PARENT ->
                NetFactory.retrofitService.parentList(token = getValue(LOGIN_TOKEN,""))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : Subscriber<List<CommentList>>() {
                            override fun onNext(t: List<CommentList>?) {
                                mAdapter = CommentAdapter(t!!)

                            }

                            override fun onCompleted() {}
                            override fun onError(e: Throwable?) {
                                e?.printStackTrace()
                            }
                        })

        }

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.send_comment_btn ->
                SendCommentActivity.newIntent(context!!)
        }

    }

}