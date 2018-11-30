package com.example.kolibreath.shconnection.ui.main.fragment

import CLASS_ID
import PAGE_NAME
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
import com.example.kolibreath.shconnection.ui.ShareActivity
import org.jetbrains.anko.support.v4.find
import rx.Subscriber

/**
 * 家校圈主页
 */
class HomeFragment: BaseFragment(), View.OnClickListener , SwipeRefreshLayout.OnRefreshListener{

    var mIsRefresh: Boolean = false
    lateinit var mAdapter: HomeAdapter
    lateinit var mList : ArrayList<FeedBean>()
    lateinit var recyclerView: RecyclerView
    lateinit var button: FloatingActionButton
    lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun getLayoutResources(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        recyclerView = find(R.id.rv_news) as RecyclerView
        button = find(R.id.share_btn) as FloatingActionButton
        swipeRefreshLayout = find(R.id.srl_news) as SwipeRefreshLayout



    }

    override fun initData() {
        NetFactory.retrofitService.feed(pagenum = PAGE_NAME,classId = CLASS_ID)
                .subscribe(object : Subscriber<FeedBean>() {
                    override fun onCompleted() {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onError(e: Throwable?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onNext(t: FeedBean?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })
                
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.share_btn -> {
                val intent = Intent()
                intent.setClass(context, ShareActivity::class.java)
            }
        }
    }

    override fun onRefresh() {
        if (!mIsRefresh) {
            mIsRefresh = true
            initData()
        }
    }
}