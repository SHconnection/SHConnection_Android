package com.example.kolibreath.shconnection.base.net

import android.support.annotation.IdRes
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import rx.Observable

interface RetrofitService{

  companion object {
    val BASE_URL = "https://api.douban.com/v2/book/"
  }

  @GET("{id}")
  fun test(@Path("id") idRes:Int): Observable<Any>

  /***
   * 经过加密的用户名和用户密码
   */
  @POST()
  fun login(userName:String, userPassword:String):Observable<Any>
}