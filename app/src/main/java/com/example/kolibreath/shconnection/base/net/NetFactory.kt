package com.example.kolibreath.shconnection.base.net

public class NetFactory private constructor(){


  companion object {
    fun create() = RetrofitWrapper.retrofit()

    val retrofitService  by lazy {
      create()
    }
  }
}