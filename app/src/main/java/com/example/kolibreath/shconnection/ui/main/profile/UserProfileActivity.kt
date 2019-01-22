package com.example.kolibreath.shconnection.ui.main.profile

import ID
import LOGIN_TOKEN
import USER_PARENT
import USER_TEACHER
import USER_TYPE
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.Person
import com.example.kolibreath.shconnection.base.net.NetFactory
import com.example.kolibreath.shconnection.base.ui.ToolbarActivity
import com.example.kolibreath.shconnection.extensions.getValue
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * 1.用户类型默认为老师
 *
 * 2.这个参数的key = modifiable
 * 如果传入modifiable == true 则可以修改
 */
class UserProfileActivity : ToolbarActivity(){

    lateinit var ivAvatar :CircleImageView
    lateinit var edtUserName :EditText
    lateinit var edtUserTel :EditText
    lateinit var edtUserWechat:EditText
    lateinit var edtUserIntro :EditText
    lateinit var edtUserSubject :EditText
    lateinit var edtUserTitle :EditText

    //todo test 这样在companion中传递参数是否可行
    companion object {
        //老师或者是家长的id的id默认为-1
        //用户类型
        var mUserType = getValue(USER_TYPE,USER_TEACHER)
        var id:Int = -1

        //跳转到个人中心 wid 是 当前用户（老师或者是家长）的id 可能是wid 或者是 sid
        //功能：
        //通过home主页左上角中别跳转到自己的个人中心
        //通过home主页feed中别跳转到别人的的个人中心
        //从通讯录中跳转到别人(老师或者家长)的个人中心
        fun start(context:Context,wid:Int,userType: Int){
            context.startActivity(Intent(context,UserProfileActivity::class.java))
            this.id = wid
            this.mUserType = userType
        }
    }


    private fun initView(modifiable : Boolean) {
        edtUserName = findViewById(R.id.edt_name)

        ivAvatar = findViewById(R.id.iv_avatar)

        edtUserTel = findViewById(R.id.edt_tel)

        edtUserWechat = findViewById(R.id.edt_wechat)

        edtUserIntro = findViewById(R.id.edt_intro)

        edtUserSubject = findViewById(R.id.edt_subject)

        edtUserTitle = findViewById(R.id.edt_rate)

        if (modifiable) {
            edtUserTel.isFocusable = false
            edtUserWechat.isFocusable = false
            edtUserIntro.isFocusable = false
            edtUserSubject.isFocusable = false
            edtUserName.isFocusable = false
            edtUserTitle.isFocusable = false

        }
    }


    //todo 根据api文档拿到相关的数据结构
    private fun getProfile(userType :Int){
        when(userType){
            USER_TEACHER ->{
              NetFactory.retrofitService
                      .teacherProfile(getValue(LOGIN_TOKEN,""))
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .subscribe(object:Subscriber<Person>(){
                          override fun onNext(t: Person?) {
                              setViews(person = t!!)
                          }
                          override fun onCompleted() {}
                          override fun onError(e: Throwable?) { e!!.printStackTrace() }
                      })
            }
            USER_PARENT ->  {
                NetFactory.retrofitService
                        .parentProfile(getValue(LOGIN_TOKEN,""))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object:Subscriber<Person>(){
                            override fun onNext(t: Person?) {
                                setViews(person = t!!)
                            }
                            override fun onCompleted() {}
                            override fun onError(e: Throwable?) { e!!.printStackTrace() }
                        })
            }
        }
    }

    //设置一下相关信息
    private fun setViews(person: Person){
        edtUserName.setText(person.name)
        edtUserTel.setText(person.tel)
        edtUserWechat.setText(person.wechat)
        edtUserIntro.setText(person.intro)
        edtUserSubject.setText(person.subject)
        edtUserTitle.setText(person.title)

        Picasso.get().load(person.avatar).into(ivAvatar)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        //初始化
        initView(intent.getBooleanExtra("modifiable",false))
        getProfile(UserProfileActivity.mUserType)
    }
}