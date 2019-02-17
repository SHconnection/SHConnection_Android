package com.example.kolibreath.shconnection.ui


import ID
import LOGIN_TOKEN
import USER_PARENT
import USER_TEACHER
import USER_TYPE
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.CommentBody
import com.example.kolibreath.shconnection.base.Score
import com.example.kolibreath.shconnection.base.net.NetFactory
import com.example.kolibreath.shconnection.base.ui.ToolbarActivity
import com.example.kolibreath.shconnection.extensions.getValue
import com.example.kolibreath.shconnection.extensions.showSnackBarShort
import org.jetbrains.anko.find
import org.json.JSONObject
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * 家长和老师添加评价以及评分界面
 */
class SendCommentActivity : ToolbarActivity(), SeekBar.OnSeekBarChangeListener{

    lateinit var mEdt : EditText
    lateinit var mSorce1 : SeekBar
    lateinit var mSorce2 : SeekBar
    lateinit var mSorce3 : SeekBar
    lateinit var mSorce4 : SeekBar
    lateinit var mScore5 : SeekBar
    lateinit var mBtnSend : Button
    lateinit var mTv1: TextView
    lateinit var mTv2: TextView
    lateinit var mTv3: TextView
    lateinit var mTv4: TextView
    lateinit var mTv5: TextView
    var scoreList : MutableList<Score> = ArrayList()


    lateinit var mBtnGraph:TextView
    lateinit var commentBody:CommentBody

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_comment)
        initView()
    }
    companion object {
        fun newIntent(context: Context){
            context.startActivity(Intent(context,SendCommentActivity::class.java))
        }
    }

    private fun initView(){
        mBtnGraph =  find(R.id.btn_graph)
        mBtnGraph.setOnClickListener { GraphActivity.start(this@SendCommentActivity) }
        mEdt = findViewById(R.id.edt_parent_comment)
        mBtnSend = findViewById(R.id.btn_send_comment)
        mSorce1 = findViewById(R.id.seekBar1)
        mSorce2 = findViewById(R.id.seekBar2)
        mSorce3 = findViewById(R.id.seekBar3)
        mSorce4 = findViewById(R.id.seekBar4)
        mScore5 = findViewById(R.id.seekBar5)
        mSorce1.setOnSeekBarChangeListener(this)
        mSorce2.setOnSeekBarChangeListener(this)
        mSorce3.setOnSeekBarChangeListener(this)
        mSorce4.setOnSeekBarChangeListener(this)
        mScore5.setOnSeekBarChangeListener(this)
        mBtnSend.setOnClickListener{ sendComment() }
        mTv1 = findViewById(R.id.score1)
        mTv2 = findViewById(R.id.score2)
        mTv3 = findViewById(R.id.score3)
        mTv4 = findViewById(R.id.score4)
        mTv5 = findViewById(R.id.score5)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        when (seekBar?.id){
            R.id.seekBar1 -> {
                mTv1.text = progress.toString()
                scoreList.add(Score("活动水平",progress))
            }
            R.id.seekBar2 -> {
                mTv2.text = progress.toString()
                scoreList.add(Score("作息规律",progress))
            }
            R.id.seekBar3 -> {
                mTv3.text = progress.toString()
                scoreList.add(Score("情绪状况",progress))
            }
            R.id.seekBar4 -> {
                mTv4.text = progress.toString()
                scoreList.add(Score("专注力",progress))
            }
            R.id.seekBar5 -> {
                mTv5.text = progress.toString()
                scoreList.add(Score("礼貌素质",progress))
            }
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {


    }

    // 家长发送评价
    private fun sendComment(){
        commentBody = CommentBody(mEdt.text.toString(),scoreList)
        when(getValue(USER_TYPE,-1)){
            USER_TEACHER ->
                NetFactory.retrofitService.teacherComment(commentBody = commentBody, token = getValue(LOGIN_TOKEN,""),sid = getValue(ID,-1))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : Subscriber<Any>(){
                            override fun onNext(t: Any?) {
                                when (t){
                                    200 -> showSnackBarShort("发送成功!")
                                    403 -> showSnackBarShort("发送失败！")
                                }
                            }

                            override fun onCompleted() {
                                mEdt.text.clear()
                                mSorce1.progress = 0
                                mSorce2.progress = 0
                                mSorce3.progress = 0
                                mSorce4.progress = 0
                                mScore5.progress = 0
                            }

                            override fun onError(e: Throwable?) {
                                e!!.printStackTrace()
                            }
                        })
            USER_PARENT ->
                NetFactory.retrofitService.parentComment(commentBody = commentBody, token = getValue(LOGIN_TOKEN,""),sid = getValue(ID,-1))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : Subscriber<Any>(){
                            override fun onNext(t: Any?) {
                                when (t){
                                    200 -> showSnackBarShort("发送成功!")
                                    403 -> showSnackBarShort("发送失败！")
                                }
                            }
                            override fun onCompleted() {
                                finish()
                            }
                            override fun onError(e: Throwable?) {
                                e!!.printStackTrace()
                            }
                        })
        }

    }


}