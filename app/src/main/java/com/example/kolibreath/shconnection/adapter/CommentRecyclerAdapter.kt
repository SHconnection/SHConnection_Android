package com.example.kolibreath.shconnection.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

open class CommentRecyclerAdapter<T> : RecyclerView.Adapter<CommentRecyclerAdapter.ViewHolder<T>> {

    var context:Context ?= null
    var mList: List<T> ?= null

    constructor(context: Context, list: List<T>){
        this.context = context
        this.mList = list
    }

    override fun onBindViewHolder(p0: CommentRecyclerAdapter.ViewHolder<T>, p1: Int) {
        TODO("not implemented")
    }

    override fun getItemCount(): Int {
        TODO("not implemented")
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CommentRecyclerAdapter.ViewHolder<T> {
        TODO("not implemented")
    }

    abstract class ViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var ivImage: ImageView? = null
        var tvDesc: TextView? = null
    }
}