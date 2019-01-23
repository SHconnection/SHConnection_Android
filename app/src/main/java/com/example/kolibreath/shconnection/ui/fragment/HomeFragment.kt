package com.example.kolibreath.shconnection.ui.main.fragment

import CLASS_ID
import PAGE_NAME
import USER_PARENT
import USER_TEACHER
import USER_TYPE
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.adapter.HomeAdapter
import com.example.kolibreath.shconnection.base.Feed
import com.example.kolibreath.shconnection.base.net.NetFactory
import com.example.kolibreath.shconnection.base.ui.BaseFragment
import com.example.kolibreath.shconnection.extensions.getValue
import com.example.kolibreath.shconnection.ui.ViewPictureActivity
import org.jetbrains.anko.support.v4.find
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * 家校圈主页
 */

//todo 查看这里的代码修改这里！
class HomeFragment: BaseFragment(), SwipeRefreshLayout.OnRefreshListener{

    lateinit var mHomeAdapter: HomeAdapter
    lateinit var mList :List<Feed>
    lateinit var recyclerView: RecyclerView
    lateinit var button: FloatingActionButton
    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    lateinit var mFeedBean: Feed


    override fun getLayoutResources(): Int {
        return R.layout.fragment_home
    }

    @SuppressLint("RestrictedApi")
    override fun initView() {
        recyclerView = find(R.id.rv_news) as RecyclerView
        button = find(R.id.share_btn) as FloatingActionButton

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
        NetFactory.retrofitService.feed(pagenum = PAGE_NAME,classId = getValue(CLASS_ID,""))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<Feed>() {
                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable?) {
                        e?.printStackTrace()
                    }

                    override fun onNext(t: Feed?) {
                        mFeedBean = t!!
                        mHomeAdapter = HomeAdapter(mFeedBean)
                        recyclerView.adapter = mHomeAdapter
                        recyclerView.layoutManager = LinearLayoutManager(activity)
                    }
                })
                
    }

    override fun onRefresh() {
        initData()
    }
}