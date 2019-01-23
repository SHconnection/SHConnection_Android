package com.example.kolibreath.shconnection.ui.main.profile

import LOGIN_TOKEN
import USER_PARENT
import USER_TEACHER
import USER_TYPE
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.adapter.SettingAdapter
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

    lateinit var ivEdit:ImageView

    //保存获取的信息
    lateinit var mPerson: Person

    val EDITABLE = "editable"
    val PERSON_INFO:String = "person_info"

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

        //从这个页面跳转到修改信息的页面
        fun start(context: Context,person: Person){
            context.startActivity(Intent(context,UserProfileActivity::class.java).apply {
                putExtra("editable",false)
                putExtra("person_info",person)
            })
        }
    }



    private fun initView() {
        edtUserName = findViewById(R.id.edt_name)
        ivAvatar = findViewById(R.id.iv_avatar)
        edtUserTel = findViewById(R.id.edt_tel)
        edtUserWechat = findViewById(R.id.edt_wechat)
        edtUserIntro = findViewById(R.id.edt_intro)
        edtUserSubject = findViewById(R.id.edt_subject)
        edtUserTitle = findViewById(R.id.edt_title)

        ivEdit = findViewById(R.id.iv_edit)
        ivEdit.setOnClickListener {
            //重新启动这个页面进行个人信息的修改
            UserProfileActivity.start(this@UserProfileActivity,mPerson)
            finish()
        }

        val editTextViews = arrayOf(edtUserTel,edtUserName,edtUserTitle,edtUserWechat,edtUserSubject
        ,edtUserIntro)
        if (isEditable()) {
            for(edit in editTextViews){
                edit.setFocusable(false)
                edit.isFocusableInTouchMode = false
                edit.isLongClickable = false
                edit.inputType = InputType.TYPE_NULL
                edit.setCursorVisible(true);
            }
        }

        findViewById<TextView>(R.id.tv_setting).apply {
            setOnClickListener {
                showBottomDialog()
            }
        }


    }


    //如果是从这个页面直接过来编辑的话 不需要加载
    private fun getProfile(){
        val userType = mUserType
        if(isEditable()){
            setViews(intent.getSerializableExtra(PERSON_INFO) as Person)
            return
        }
        when(userType){
            USER_TEACHER ->{
              NetFactory.retrofitService
                      .teacherProfile(getValue(LOGIN_TOKEN,""))
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .subscribe(object:Subscriber<Person>(){
                          override fun onNext(t: Person?) {
                              mPerson = t!!
                              setViews(person = t)
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
                                mPerson = t!!
                                setViews(person = t)
                            }
                            override fun onCompleted() {}
                            override fun onError(e: Throwable?) { e!!.printStackTrace() }
                        })
            }
        }
    }

    val isEditable = {
        val intent = intent
        val editable = intent.getBooleanExtra(EDITABLE,false)
        editable
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

    private fun showBottomDialog(){
        val bottomDialog = BottomSheetDialog(this@UserProfileActivity)
        val view = this@UserProfileActivity.layoutInflater.inflate(R.layout.view_bottom_dialog,null)
        bottomDialog.setContentView(view)
        bottomDialog.show()


        val list = when(mUserType){
            USER_PARENT ->{
                listOf<String>("退出登录")
            }else ->{
                listOf<String>("退出登录","切换班级")
            }
        }

        val rvSetting :RecyclerView = view.findViewById(R.id.rv_setting)
        val adapter = SettingAdapter(list)
        rvSetting.layoutManager = LinearLayoutManager(this@UserProfileActivity)
        rvSetting.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        //初始化
        initView()
        getProfile()
    }
}