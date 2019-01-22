package com.example.kolibreath.shconnection.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.kolibreath.shconnection.R
import org.jetbrains.anko.layoutInflater

class SettingAdapter(val list: List<String>): RecyclerView.Adapter<SettingAdapter.SettingViewHolder>(){

    lateinit var context:Context
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SettingViewHolder {
        context = p0.context
        return SettingViewHolder(context.layoutInflater.inflate(R.layout.item_setting,p0,false))
    }

    override fun getItemCount(): Int  = list.size

    override fun onBindViewHolder(holder: SettingViewHolder, position: Int) {
        holder.tvSettingItem.text = list[position]
    }

    class SettingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvSettingItem:TextView = itemView.findViewById(R.id.tv_setting_item)
    }
}