package com.example.kolibreath.shconnection.ui.main.fragment

import CLASS_ID
import PAGE_NAME
import USER_PARENT
import USER_TEACHER
import USER_TYPE
import android.content.Intent
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.adapter.HomeAdapter
import com.example.kolibreath.shconnection.base.FeedBean
import com.example.kolibreath.shconnection.base.net.NetFactory
import com.example.kolibreath.shconnection.base.ui.BaseFragment
import com.example.kolibreath.shconnection.ui.ViewPictureActivity
import org.jetbrains.anko.support.v4.find
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * 家校圈主页
 */
class HomeFragment: BaseFragment(), SwipeRefreshLayout.OnRefreshListener{

    var mIsRefresh: Boolean = false
    lateinit var mAdapter: HomeAdapter
    lateinit var mList :List<FeedBean.Feeds>
    lateinit var recyclerView: RecyclerView
    lateinit var button: FloatingActionButton
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var mFeedBean: FeedBean


    override fun getLayoutResources(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        recyclerView = find(R.id.rv_news) as RecyclerView
        button = find(R.id.share_btn) as FloatingActionButton
        swipeRefreshLayout = find(R.id.srl_news) as SwipeRefreshLayout
        when(USER_TYPE){
            USER_PARENT.toString() -> button.visibility = View.GONE
            USER_TEACHER.toString() -> button.setOnClickListener {
                val intent = Intent()
                intent.setClass(context, ViewPictureActivity::class.java)
            }
        }
    }

    override fun initData() {
        NetFactory.retrofitService.feed(pagenum = PAGE_NAME,classId = CLASS_ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<FeedBean>() {
                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable?) {
                        e?.printStackTrace()
                    }

                    override fun onNext(t: FeedBean?) {
                        mFeedBean = t!!
                        mList = mFeedBean.feeds
                        mAdapter = HomeAdapter(context!!,mList)
                    }
                })
                
    }

    override fun onRefresh() {
        if (!mIsRefresh) {
            mIsRefresh = true
            initData()
        }
    }
}