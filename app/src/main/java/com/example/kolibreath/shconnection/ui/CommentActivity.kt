package com.example.kolibreath.shconnection.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.CommentList
import com.example.kolibreath.shconnection.base.ui.ToolbarActivity
import org.jetbrains.anko.find

/**
 * 点击后进入的 评论详情界面
 */
class CommentActivity : ToolbarActivity() {

    lateinit var mBtnGraph:TextView
    lateinit var name:TextView
    lateinit var detail:TextView
    lateinit var scoreList: MutableList<SeekBar>
    lateinit var mTv1:TextView
    lateinit var mTv2:TextView
    lateinit var mTv3:TextView
    lateinit var mTv4:TextView
    lateinit var mTv5:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acticity_comment_detail)
        initView()
    }

    companion object {
        lateinit var mComment : CommentList
        fun newIntent(context: Context,comment: CommentList): Intent {
            val intent = Intent(context, CommentActivity::class.java)
            mComment = comment
            return intent
        }
    }

    private fun initView(){
        mBtnGraph = find(R.id.btn_graph)
        mBtnGraph.setOnClickListener {  }
        name = find(R.id.eval_d_name)
        detail = find(R.id.eval_d_detail)
        name.text = mComment.name
        detail.text = mComment.detail
        val seekBar1 = find<SeekBar>(R.id.comment_score1)
        val seekBar2 = find<SeekBar>(R.id.comment_score2)
        val seekBar3 = find<SeekBar>(R.id.comment_score3)
        val seekBar4 = find<SeekBar>(R.id.comment_score4)
        val seekBar5 = find<SeekBar>(R.id.comment_score5)
        scoreList = ArrayList(5)
        scoreList.add(0,seekBar1)
        scoreList.add(1,seekBar2)
        scoreList.add(2,seekBar3)
        scoreList.add(3,seekBar4)
        scoreList.add(4,seekBar5)
        mTv1 = findViewById(R.id.eval_score1)
        mTv2 = findViewById(R.id.eval_score2)
        mTv3 = findViewById(R.id.eval_score3)
        mTv4 = findViewById(R.id.eval_score4)
        mTv5 = findViewById(R.id.eval_score5)
        mTv1.text = mComment.scores[0].score.toString()
        mTv2.text = mComment.scores[1].score.toString()
        mTv3.text = mComment.scores[2].score.toString()
        mTv4.text = mComment.scores[3].score.toString()
        mTv5.text = mComment.scores[4].score.toString()
        for (index in 0..4){
            scoreList[index].progress = mComment.scores[index].score
        }
    }
}