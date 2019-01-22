package com.example.kolibreath.shconnection.base.net


import com.example.kolibreath.shconnection.base.*
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
  fun teacherChangeProfile(@Header("token") token:String,@Body profile: Profile):Observable<Any>

  /**
   * 获取老师的通讯录信息
   */
  @GET("teacher/info/")
  fun teacherProfile(@Header("token") token: String):Observable<Profile>

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
   * 上传图片
   */
  @POST()
  fun login():Observable<Any>


  /**
   * feed流
   */

  //todo 修改feed 的api
  @GET("feeds/{pagenum}/class/{class_id}/")
  fun feed(@Path("pagenum") pagenum: Int,
           @Path("class_id") classId: String):Observable<FeedBean>

  /**
   * feed详情
   */
  @GET("feed/{feedid}")
  fun feedDetails(@Path("feedid")feedId: Int,
                  @Header("token") token: String):Observable<FeedDetails>

  /**
   * 阅读某个feed
   */
  @POST("feed/{feedid}/read/")
  fun feedRead(@Path("feedid")feedId: Int,
               @Header("token") token:String):Observable<Any>

  /**
   * 喜欢某个feed
   */
  @POST("feed/{feedid}/like/")
  fun feedLike(@Path("feedid")feedId: Int,
               @Header("token") token:String):Observable<Any>

  /**
   * 发送评论
   */
  @POST("feed/{feedid}/tvComment/")
  fun feedComment(@Path("feedid")feedId: Int,
                  @Header("token")token: String,
                  @Body content: String):Observable<Any>

  /**
   * 获取某个feed的评论
   */
  @GET("feed/{feedid}/comments")
  fun feedGetComments(@Path("feedid")feedId: Int,
                      @Header("token")token: String):Observable<FeedComments>

  /**
   * 获取某个老师对某个孩子的评价
   */
  @GET("evaluation/teacher/all/")
  fun teacherPerComment(@Header("token")token: String):Observable<ScoreComment>


  /**
   * 获取家长对孩子的评价
   */
  @GET("evaluation/parent/all/")
  fun parentPerComment(@Header("token")token: String):Observable<ScoreComment>

  /**
   * 老师端孩子评论list
   */
  @GET("evaluation/teacher/view/")
  fun teacherList(@Header("token")token: String):Observable<TeacherList>

  /**
   * 家长端评论list
   */
  @GET("evaluation/parent/view/")
  fun parentList(@Header("token")token: String):Observable<ParentList>

  /**
   * 老师发送孩子评价
   */
  @POST("evaluation/teacher/")
  fun teacherComment(@Body commentBody: CommentBody,
                     @Header("token")token: String):Observable<Any>


  /**
   * 家长发送孩子评价
   */
  @POST("evaluation/parent/")
  fun parentComment(@Body commentBody: CommentBody,
                    @Header("token")token: String):Observable<Any>

  /**
   * 老师端 某个老师所发的所有评价
   */
  @GET("evaluation/teacher/teacher/")
  fun teacherPerAll(@Header("token")token: String):Observable<TeacherCommentAll>

  /**
   * 老师端 所有老师对某个家长孩子的所有评价
   */
  @GET("evaluation/teacher/parent/")
  fun teacherChildAll(@Header("token")token: String):Observable<ChildCommentAll>

  /**
   * 家长端 某个老师对该家长孩子的所有评价
   */
  @GET("evaluation/parent/teacher/")
  fun parentTeacherComment(@Header("token")token: String):Observable<TeacherPerComment>

  /**
   * 家长端 家长自己的所有评价
   */
  @GET("evaluation/parent/parent/")
  fun parentSelfComment(@Header("token")token: String):Observable<ParentPerComment>

  /**
   * 返回班级通讯录
   */
  @GET("class/info/")
  fun classAddress(@Header("token")token: String,@Query("cid") cid:String):Observable<AddressBean>

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
  fun parentChangeProfile(@Header("token") token :String,@Body profile: Profile):Observable<Any>

  /**
   * 获取家长资料
   */
  @POST("parent/info/")
  fun parentProfile(@Header("token") token:String):Observable<Profile>


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
}