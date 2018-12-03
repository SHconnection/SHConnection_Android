package com.example.kolibreath.shconnection.adapter

import LOGIN_TOKEN
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.FeedBean
import com.example.kolibreath.shconnection.base.net.NetFactory
import de.hdodenhof.circleimageview.CircleImageView
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * feed流
 */
class HomeAdapter(context: Context, list: List<FeedBean.Feeds>): RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    var list: List<FeedBean.Feeds>? = null
    var context: Context? = null
    var inflater: LayoutInflater? = null

    init {
        this.context = context
        this.list = list
        this.inflater = inflater
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(inflater!!.inflate(R.layout.item_news, parent, false), context!!)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {


        holder.name!!.text = list!![position].teacherSimpleInfo.name
        holder.sort!!.text = list!![position].type
        holder.comment!!.text = list!![position].content
        if (list!![position].liked) {
            holder.btnUnread!!.setText("已读")
        }else{
            holder.btnUnread!!.setText("未读")
        }
        holder.btnComment!!.setOnClickListener {

            NetFactory.retrofitService.feedComment(feedId = position,token = LOGIN_TOKEN,content = "content")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Subscriber<Any>() {
                        override fun onNext(t: Any?) {
                            TODO()
                        }
                        override fun onCompleted() {
                        }
                        override fun onError(e: Throwable?){ e!!.printStackTrace() }
                    })
        }
    }



    class HomeViewHolder(itemView: View, context: Context): RecyclerView.ViewHolder(itemView) {
        var photo: CircleImageView ?= null
        var name: TextView ?= null
        var sort: TextView ?= null
        var comment: TextView ?= null
        var btnUnread: Button ?= null
        var btnComment: Button ?= null

        init {
            photo = itemView.findViewById(R.id.iv_news_avatar)
            name = itemView.findViewById(R.id.tv_news_name)
            sort = itemView.findViewById(R.id.tv_news_sort)
            comment = itemView.findViewById(R.id.tv_news)
            btnUnread = itemView.findViewById(R.id.btn_news_unread)
            btnComment = itemView.findViewById(R.id.btn_news_comment)

        }

    }
}


