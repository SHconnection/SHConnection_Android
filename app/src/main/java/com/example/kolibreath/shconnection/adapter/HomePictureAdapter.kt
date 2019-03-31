package com.example.kolibreath.shconnection.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.opengl.Visibility
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.widget.CenterDialogFragment
import com.example.kolibreath.shconnection.extensions.findView
import com.squareup.picasso.Picasso
import org.jetbrains.anko.image
import org.jetbrains.anko.imageBitmap
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

        val imageView = ImageView(context)
        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE)
        imageView.layoutParams = layoutParams
        val curActivity = context as Activity
        val layout = curActivity.findViewById<RelativeLayout>(R.id.fragment_home)

        viewHolder.ivHomePicture.setOnClickListener {
            val bitmap = viewHolder.ivHomePicture.image
            imageView.setImageDrawable(bitmap)
            layout.addView(imageView)
//            dialog.show()

            imageView.setOnClickListener { layout.removeView(imageView) }
        }
    }

    override fun getItemCount(): Int {
        return pictures.size
    }

    class HomePictureViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        //只有一个图片显示
        val ivHomePicture:ImageView = itemView.findViewById(R.id.iv_home_picture) as ImageView


    }
}