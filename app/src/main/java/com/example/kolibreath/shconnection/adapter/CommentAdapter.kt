
package com.example.kolibreath.shconnection.adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.CommentList
import com.example.kolibreath.shconnection.ui.CommentActivity
import org.jetbrains.anko.bundleOf
import org.jetbrains.anko.find
import java.io.Serializable

class CommentAdapter(val commentList: List<CommentList>):RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        holder.name.text = commentList[p1].name
        holder.detail.text = commentList[p1].detail
        holder.linearLayout.setOnClickListener {
            v ->
                holder.context.startActivity(CommentActivity.newIntent(holder.context,commentList[p1]))}

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_eval,p0,false))
    }


    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.find(R.id.eval_name)
        val detail: TextView = itemView.find(R.id.eval_detail)
        val linearLayout: LinearLayout = itemView.find(R.id.eval_ll)
        val context: Context = itemView.context
    }
}