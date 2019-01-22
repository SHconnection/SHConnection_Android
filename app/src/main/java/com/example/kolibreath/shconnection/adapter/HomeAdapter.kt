package com.example.kolibreath.shconnection.adapter

import LOGIN_TOKEN
import USER_TEACHER
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.Feed
import com.example.kolibreath.shconnection.base.net.NetFactory
import com.example.kolibreath.shconnection.extensions.getValue
import com.example.kolibreath.shconnection.extensions.showSnackBarShort
import com.example.kolibreath.shconnection.ui.main.profile.UserProfileActivity
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import org.jetbrains.anko.layoutInflater
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.text.SimpleDateFormat

/**
 * feed流
 */
class HomeAdapter(val feed: Feed): RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    val list = feed.feeds
    lateinit var context: Context
    lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        context = parent.context
        //todo test this!
        inflater = context.layoutInflater
        return HomeViewHolder(inflater.inflate(R.layout.item_news, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {

        Picasso.get().load(list[position].teacherSimpleInfo.avatar).into(holder.ivAvatar)
        holder.ivAvatar.setOnClickListener { UserProfileActivity.start(
                context = context, wid = list[position].id, userType = USER_TEACHER) }

        holder.tvName.text = list[position].teacherSimpleInfo.name
        holder.tvType.text = list[position].type
        val newsTime = SimpleDateFormat("YYYYMMDDHHMM").format(list[position].time)
        holder.tvNewtime.text = newsTime

        //评论的相关list
        val commentList = list[position].comments
        val commentAdapter = HomeCommentsAdapter(commentList)
        holder.rvNewComment.adapter = commentAdapter
        holder.rvNewComment.layoutManager = LinearLayoutManager(context)


        //图片的相关list
        val picurls = list[position].picture_urls
        val homePictureAdapter = HomePictureAdapter(picurls)
        holder.rvNewPictures.adapter = homePictureAdapter
        holder.rvNewPictures.layoutManager = GridLayoutManager(context,3)


        //设置已读和未读
        //设置这一条动态已经被读
        NetFactory.retrofitService
                .feedRead(list[position].id, getValue(LOGIN_TOKEN,""))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object:Subscriber<Any>(){
                    override fun onNext(t: Any?) {holder.btnUnread.text = "已读"}
                    override fun onCompleted() {}
                    override fun onError(e: Throwable?) {e!!.printStackTrace()}
                })

        holder.btnComment.setOnClickListener {
            AlertDialog.Builder(context).apply {
                    setTitle("编写新的评论")
                    .setView(context.layoutInflater.inflate(R.layout.view_home_comment_dialog, null, false))
                    .setPositiveButton("确定发送") { dialog, which ->
                        NetFactory.retrofitService.feedComment(feedId = position, token = LOGIN_TOKEN, content = "content")
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(object : Subscriber<Any>() {
                                    override fun onNext(t: Any?) {
                                        val activity = context as Activity
                                        //todo dialog 会不会dismiss？
                                        activity.showSnackBarShort("评论已经发送成功")
                                    }

                                    override fun onCompleted() {
                                    }

                                    override fun onError(e: Throwable?) {
                                        e!!.printStackTrace()
                                    }
                                })
                    }
            }.show()
        }
    }



    //展示feed内容
    class HomeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val ivAvatar: CircleImageView  =   itemView.findViewById(R.id.iv_news_avatar)
        val tvName: TextView          =   itemView.findViewById(R.id.tv_news_name)
        val tvType: TextView          =   itemView.findViewById(R.id.tv_news_type)
        val tvComment: TextView       =   itemView.findViewById(R.id.tv_comment)
        val btnUnread: Button       =   itemView.findViewById(R.id.btn_news_unread)
        val tvNewtime:TextView     = itemView.findViewById(R.id.tv_new_time)
        val tvNews:TextView = itemView.findViewById(R.id.tv_news)
        val rvNewComment: RecyclerView = itemView.findViewById(R.id.rv_news_comment)
        val rvNewPictures:RecyclerView = itemView.findViewById(R.id.rv_news_pictures)

        //todo 向comment中增加item
        val btnComment: Button      =   itemView.findViewById(R.id.btn_news_comment)

    }
}


