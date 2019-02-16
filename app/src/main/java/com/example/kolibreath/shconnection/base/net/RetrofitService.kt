package com.example.kolibreath.shconnection.base.net


import com.example.kolibreath.shconnection.base.*
import okhttp3.ResponseBody
import retrofit2.http.*

import rx.Observable

interface RetrofitService{

  companion object {
    const val BASE_URL = "http://39.108.79.110:2000/api/"
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
  fun teacherChangeProfile(@Header("token") token:String,@Body person: Person):Observable<Any>

  /**
   * 获取老师的通讯录信息
   */
  @GET("teacher/info/")
  fun teacherProfile(@Header("token") token: String):Observable<Person>

  /**
   * 老师注册并且加入一个班级
   * 此时老师是没有注册的
   */
  @POST("teacher/init/addclass/{classid}/")
  fun teacherInitJoinClass(@Path("classid") classId:String,
      @Body teacherInit: TeacherInit):Observable<Any>

  /**
   * 家长在没有注册的情况下
   * 同时初始化并且加入一个班级
   */
  @POST("parent/addclass/{classid}/")
  fun parentInitJoinClass(@Path("classid") classId: String,
      @Body parentInit: ParentInit):Observable<Any>

  /**
   * 老师创建一个班级
   */
  @POST("init/class/")
  fun teacherCreateClass(@Body teacherCreateClassBody: TeacherCreateClassBody):Observable<CreatedClassId>


  /**
   * feed流
   */

  @GET("feeds/{pagenum}/class/{class_id}/")
  fun feed(@Path("pagenum") pagenum: Int,
           @Path("class_id") class_id: String,
           @Header("token") token:String):Observable<Feed>
  /**
   * 阅读某个feed
   */
  @POST("feed/{feedid}/read/")
  fun feedRead(@Path("feedid")feedId: Int,
               @Header("token") token:String):Observable<Any>


  /**
   * 发送评论
   */
  @POST("feed/{feedid}/comment/")
  fun feedComment(@Path("feedid")feedId: Int,
                  @Header("token")token: String,
                  @Body content: HomeComment):Observable<Any>


    //todo 奇怪的代码
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
  fun parentChangeProfile(@Header("token") token :String,@Body person: Person):Observable<Any>

  /**
   * 获取家长资料
   */
  @POST("parent/info/")
  fun parentProfile(@Header("token") token:String):Observable<Person>


  /**
   * 上传一个动态
   */

  @POST("feed/")
  fun postFeed(@Header("token")token:String,@Body feedBody: FeedBody):
      Observable<Any>

  /***
   * 注册一个班主任帐号
   */
  @POST("mainteacher/signup/")
  fun signUpMainTeacher(@Body mainTeacherSignUpBody: MainTeacherSignUpBody):Observable<Any>

  /**
   * 老师端孩子评论list
   */
  @GET("evaluation/view/teacher/")
  fun teacherList(@Header("token")token: String):Observable<List<CommentList>>

  /**
   * 家长端评论list
   */
  @GET("evaluation/view/parent/")
  fun parentList(@Header("token")token: String):Observable<List<CommentList>>

  /**
   * 老师发送孩子评价
   */
  @POST("evaluation/teacher/")
  fun teacherComment(@Body commentBody: CommentBody,
                     @Header("token")token: String,@Query("sid")sid:Int):Observable<Any>

  /**
   * 家长发送孩子评价
   */
  @POST("evaluation/parent/")
  fun parentComment(@Body commentBody: CommentBody,
                    @Header("token")token: String,@Query("sid")sid:Int):Observable<Any>


  /**
   * 返回班级通讯录
   */
  @GET("class/info/")
  fun classAddress(@Header("token")token: String,@Query("cid")cid:Int):Observable<ClassAddress>

}