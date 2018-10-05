package com.example.kolibreath.shconnection.base.net

import com.example.kolibreath.shconnection.base.CreatedClassId
import com.example.kolibreath.shconnection.base.ParentInit
import com.example.kolibreath.shconnection.base.TeacherLoginBody
import com.example.kolibreath.shconnection.base.TeacherLoginToken
import com.example.kolibreath.shconnection.base.ParentLoginBody
import com.example.kolibreath.shconnection.base.ParentLoginToken
import com.example.kolibreath.shconnection.base.Profile
import com.example.kolibreath.shconnection.base.TeacherCreateClassBody
import com.example.kolibreath.shconnection.base.TeacherInit
import com.example.kolibreath.shconnection.base.TeacherSignupBody
import com.example.kolibreath.shconnection.base.TeacherSignupToken
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
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
   * 老师注册并且加入一个班级
   * 此时老师是没有注册的
   */
  @POST("/teacher/init/addclass/{classid}/")
  fun teacherInitJoinClass(@Path("classid") classId:String,
      @Body teacherInit: TeacherInit):Observable<Any>

  /**
   * 家长在没有注册的情况下
   * 同时初始化并且加入一个班级
   */
  @POST("/parent/addclass/{classid}/")
  fun parentInitJoinClass(@Path("classid") classId: String,
      @Body parentInit: ParentInit):Observable<Any>

  /**
   * 老师创建一个班级
   */
  @POST("/init/class/")
  fun teacherCreateClass(@Body teacherCreateClassBody: TeacherCreateClassBody):Observable<CreatedClassId>
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