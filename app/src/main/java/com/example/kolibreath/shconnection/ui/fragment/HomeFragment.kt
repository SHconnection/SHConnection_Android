package com.example.kolibreath.shconnection.ui.main.fragment

import CLASS_ID
import LOGIN_TOKEN
import USER_PARENT
import USER_TEACHER
import USER_TYPE
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.adapter.HomeAdapter
import com.example.kolibreath.shconnection.base.Feed
import com.example.kolibreath.shconnection.base.FeedX
import com.example.kolibreath.shconnection.base.net.NetFactory
import com.example.kolibreath.shconnection.base.ui.BaseFragment
import com.example.kolibreath.shconnection.extensions.getValue
import com.example.kolibreath.shconnection.ui.main.news.ViewPictureActivity
import org.jetbrains.anko.support.v4.find
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 * 家校圈主页
 */

//todo 查看这里的代码修改这里！
class HomeFragment: BaseFragment(), SwipeRefreshLayout.OnRefreshListener{

    lateinit var mHomeAdapter: HomeAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var button: FloatingActionButton
    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    lateinit var mFeedBean: Feed
    var mList: LinkedList<FeedX> = LinkedList()
    var pagenum = 1


    override fun getLayoutResources(): Int {
        return R.layout.fragment_home
    }

    @SuppressLint("RestrictedApi")
    override fun initView() {
        recyclerView = find(R.id.rv_news) as RecyclerView
        button = find(R.id.share_btn) as FloatingActionButton

        button.setOnClickListener {
            ViewPictureActivity.start(activity!!)
        }

        //设置swipeRefreshLayout相关属性
        mSwipeRefreshLayout = find(R.id.srl_news) as SwipeRefreshLayout
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE,Color.GREEN,Color.RED)
        mSwipeRefreshLayout.setDistanceToTriggerSync(300)
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE)
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE)
        mSwipeRefreshLayout.setOnRefreshListener(this@HomeFragment)

        when(USER_TYPE){
            USER_PARENT.toString() ->  button.visibility = View.GONE
            USER_TEACHER.toString() -> button.setOnClickListener {
                val intent = Intent()
                intent.setClass(context, ViewPictureActivity::class.java)
            }
        }
    }

    override fun initData() {
        NetFactory.retrofitService.feed(pagenum = pagenum,class_id  = getValue(CLASS_ID,""),token = getValue(LOGIN_TOKEN,""))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<Feed>() {
                    override fun onCompleted() {mSwipeRefreshLayout.isRefreshing = false}

                    override fun onError(e: Throwable?) {
                        e?.printStackTrace()
                    }

                    override fun onNext(t: Feed?) {
                        Log.d("fuck","已经加载了！")
                        mFeedBean = t!!
                        val curlist = LinkedList(mFeedBean.feeds).reversed()
                        for(item in curlist)
                            mList.addFirst(item)

                        mHomeAdapter = HomeAdapter(mList)
                        recyclerView.adapter = mHomeAdapter
                        recyclerView.layoutManager = LinearLayoutManager(activity)

                    }
                })
                
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    override fun onRefresh() {
        initData()
        pagenum++
    }
}