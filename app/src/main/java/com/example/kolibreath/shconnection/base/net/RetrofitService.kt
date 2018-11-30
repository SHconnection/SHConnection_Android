package com.example.kolibreath.shconnection.base.net

import com.example.kolibreath.shconnection.base.*
import retrofit2.http.*
import rx.Observable

interface RetrofitService{

  companion object {
    val BASE_URL = "http://112.74.88.136:2000/api/"
  }

  @GET("{id}")
  fun test(@Path("id") idRes:Int): Observable<Any>


  //todo modify this
  @POST()
  fun login():Observable<Any>


  /**
   * feed流
   */
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
  @POST("feed/{feedid}/comment/")
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
  fun classAddress(@Header("token")token: String):Observable<AddressBean>
}