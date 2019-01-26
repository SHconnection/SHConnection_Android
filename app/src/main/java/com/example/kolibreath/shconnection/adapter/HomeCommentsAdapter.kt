package com.example.kolibreath.shconnection.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.Comment
import org.jetbrains.anko.layoutInflater

class HomeCommentsAdapter(val list : List<Comment>) :RecyclerView.Adapter<HomeCommentsAdapter.HomeCommentViewHolder>(){

    lateinit var context :Context
    lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): HomeCommentViewHolder {
        context = parent.context
        inflater = context.layoutInflater
        return HomeCommentViewHolder(inflater.inflate(R.layout.item_home_comment,parent,false))
    }

    override fun onBindViewHolder(holder: HomeCommentViewHolder, position: Int) {
        holder.tvUsername.text = list[position].username
        holder.tvContent.text = list[position].content
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class HomeCommentViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val tvUsername = itemView.findViewById<TextView>(R.id.tv_commenter)!!
        val tvContent   = itemView.findViewById<TextView>(R.id.tv_comment)!!
    }
}