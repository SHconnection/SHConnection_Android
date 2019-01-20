package com.example.kolibreath.shconnection.ui


import LOGIN_TOKEN
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.CommentBody
import com.example.kolibreath.shconnection.base.net.NetFactory
import com.example.kolibreath.shconnection.base.ui.ToolbarActivity
import org.jetbrains.anko.find
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * 家长添加评价以及评分界面
 */
class ParentsCommentActivity : ToolbarActivity(), SeekBar.OnSeekBarChangeListener{

    lateinit var mEdt : EditText
    lateinit var mSorce1 : SeekBar
    lateinit var mSorce2 : SeekBar
    lateinit var mSorce3 : SeekBar
    lateinit var mSorce4 : SeekBar
    lateinit var mScore5 : SeekBar
    lateinit var mBtnSend : Button
    lateinit var scoreList : MutableList<CommentBody.Score>
    lateinit var commentBody:CommentBody


    override fun setContentView(layoutResID: Int) {
        super.setContentView(R.layout.activity_parent_comment)
        initView()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(CommentActivity.newIntent(this))
    }


    fun initView(){
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
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        when (seekBar?.id){
            R.id.seekBar1 -> scoreList.add(CommentBody.Score("活动水平",progress))
            R.id.seekBar2 -> scoreList.add(CommentBody.Score("作息规律",progress))
            R.id.seekBar3 -> scoreList.add(CommentBody.Score("情绪状况",progress))
            R.id.seekBar4 -> scoreList.add(CommentBody.Score("专注力",progress))
            R.id.seekBar5 -> scoreList.add(CommentBody.Score("礼貌素质",progress))
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {


    }

    // 家长发送评价
    private fun sendComment(){
        commentBody.setComment(mEdt.text.toString())
        commentBody.setScore(scoreList)
        NetFactory.retrofitService.parentComment(commentBody = commentBody, token = LOGIN_TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<Any>(){
                    override fun onNext(t: Any?) {
                        when (t){
                            200 -> Toast.makeText(baseContext,"评论成功！",Toast.LENGTH_SHORT).show()
                            403 -> Toast.makeText(baseContext,"发送失败！",Toast.LENGTH_SHORT).show()
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