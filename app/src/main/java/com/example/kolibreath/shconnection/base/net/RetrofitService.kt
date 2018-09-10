package com.example.kolibreath.shconnection.base.net

import android.support.annotation.IdRes
import com.example.kolibreath.shconnection.base.data.TeacherLoginBody
import com.example.kolibreath.shconnection.base.data.TeacherLoginToken
import com.example.kolibreath.shconnection.base.data.TeacherProfile
import com.example.kolibreath.shconnection.base.data.TeacherSignupBody
import com.example.kolibreath.shconnection.base.data.TeacherSignupToken
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import rx.Observable
import java.net.PasswordAuthentication

interface RetrofitService{

  companion object {
    const val BASE_URL = "https://api.douban.com/v2/book/"
  }


  /***
   * 经过加密的用户名和用户密码
   */
  @POST("teacher/login/")
  fun teacherLogin(@Body loginInfo:TeacherLoginBody):Observable<TeacherLoginToken>

  /**
   * 老师注册
   */
  @POST("teacher/signup/")
  fun teacherSignUp(@Body teacherSignupBody: TeacherSignupBody):Observable<TeacherSignupToken>

  /**
   * 老师修改个人资料
   */
  @POST("teacher/profile/")
  fun teacherChangeProfile(@Header("token") token:String,@Body teacherProfile: TeacherProfile):Observable<Any>

  /**
   * 获取老师的通讯录信息
   */
  @GET("teacher/info/")
  fun teacherProfile(tid:String,token: String):Observable<TeacherProfile>
  /**
   * 上传图片
   */
  @POST()
  fun storePicture():Observable<Any>

  /**
   * 上传一个动态
   */

  @POST()
  fun postNews(content:String,pictures:List<String>)

}