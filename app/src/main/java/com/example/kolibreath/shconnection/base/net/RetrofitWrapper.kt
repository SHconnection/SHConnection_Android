package com.example.kolibreath.shconnection.base.net

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.SECONDS

object RetrofitWrapper{

  fun retrofit():RetrofitService{
    val interceptor = HttpLoggingInterceptor();
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    val client = OkHttpClient.Builder().apply {
      addInterceptor(interceptor)
      readTimeout(25,SECONDS)
      connectTimeout(25,SECONDS)
      writeTimeout(25,SECONDS)
    }.build()

    val retrofit  = Retrofit.Builder().apply {
      client(client)
      addConverterFactory(GsonConverterFactory.create())
      addCallAdapterFactory(RxJavaCallAdapterFactory.create())
      //todo add base url
      baseUrl(RetrofitService.BASE_URL)
    }.build()

    return retrofit.create(RetrofitService::class.java)

  }
}