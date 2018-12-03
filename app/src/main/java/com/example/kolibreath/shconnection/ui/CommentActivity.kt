package com.example.kolibreath.shconnection.ui

import android.content.Context
import android.content.Intent
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.ui.ToolbarActivity

/**
 * 点击后进入的 某老师或家长的评价列表 recycleView
 */
class CommentActivity : ToolbarActivity() {

    override fun setContentView(layoutResID: Int) {
        super.setContentView(R.layout.acticity_comment_detail)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ParentsCommentActivity::class.java)
        }
    }

    fun initView(){

    }


}