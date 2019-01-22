package com.example.kolibreath.shconnection.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.kolibreath.shconnection.R
import com.squareup.picasso.Picasso
import org.jetbrains.anko.layoutInflater

class HomePictureAdapter(val pictures:List<String>): RecyclerView.Adapter<HomePictureAdapter.HomePictureViewHolder>(){

    lateinit var context:Context
    lateinit var layoutInflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): HomePictureViewHolder {
        context = parent.context
        layoutInflater = context.layoutInflater
        return HomePictureViewHolder(layoutInflater.inflate(R.layout.item_home_picture,parent,false))
    }

    override fun onBindViewHolder(viewHolder: HomePictureViewHolder, position: Int) {
        Picasso.get().load(pictures[0]).into(viewHolder.ivHomePicture)
    }

    override fun getItemCount(): Int {
        return pictures.size
    }

    class HomePictureViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        //只有一个图片显示
        val ivHomePicture:ImageView = itemView.findViewById(R.id.iv_home_picture)
    }
}