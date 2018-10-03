package com.example.kolibreath.shconnection.base.net

import com.example.kolibreath.shconnection.base.TeacherLoginBody
import com.example.kolibreath.shconnection.base.TeacherLoginToken
import com.example.kolibreath.shconnection.base.ParentLoginBody
import com.example.kolibreath.shconnection.base.ParentLoginToken
import com.example.kolibreath.shconnection.base.Profile
import com.example.kolibreath.shconnection.base.TeacherSignupBody
import com.example.kolibreath.shconnection.base.TeacherSignupToken
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import rx.Observable

interface RetrofitService{

  companion object {
    const val BASE_URL = "https://api.douban.com/v2/book/"
  }


  /***
   * 经过加密的用户名和用户密码
   */
  @POST("teacher/signin/")
  fun teacherLogin(@Body loginInfo: TeacherLoginBody):Observable<TeacherLoginToken>

  /**
   * 老师注册
   */
  @POST("teacher/signup/")
  fun teacherSignUp(@Body teacherSignupBody: TeacherSignupBody):Observable<TeacherSignupToken>

  /**
   * 老师修改个人资料
   */
  @POST("teacher/profile/")
  fun teacherChangeProfile(@Header("token") token:String,@Body profile: Profile):Observable<Any>

  /**
   * 获取老师的通讯录信息
   */
  @GET("teacher/info/")
  fun teacherProfile(tid:String,token: String):Observable<Profile>
  /**
   * 上传图片
   */
  @POST()
  fun storePicture():Observable<Any>

  /**
   * 家长登录
   */
  @POST("parent/signin/")
  fun parentLogin(@Body loginBody: ParentLoginBody):Observable<ParentLoginToken>

  /**
   * 家长修改资料
   */
  @POST("parent/profile/")
  fun parentChangeProfile(@Header("token") token :String,@Body profile: Profile):Observable<Any>

  /**
   * 获取家长资料
   */
  @POST("parent/info/")
  fun parentProfile(@Header("token") token:String,pid:String):Observable<Profile>


  /**
   * 上传一个动态
   */

  @POST()
  fun postNews(content:String,pictures:List<String>)

}