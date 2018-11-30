package com.example.kolibreath.shconnection.extensions

import android.util.Log
//import com.example.kolibreath.shconnection.extensions.QiniuUpLoader.uploader
//import com.qiniu.android.common.FixedZone
//import com.qiniu.android.http.ResponseInfo
//import com.qiniu.android.storage.Configuration
//import com.qiniu.android.storage.UpCompletionHandler
//import com.qiniu.android.storage.UpProgressHandler
//import com.qiniu.android.storage.UploadManager
//import com.qiniu.android.storage.UploadOptions
import org.json.JSONObject
import java.io.File

object QiniuUpLoader {
//
//  private val config = Configuration.Builder()
//      .apply {
//        chunkSize(512 * 1024)
//        putThreshhold(1024 * 1024)
//        connectTimeout(10)
//        useHttps(true)
//        responseTimeout(60)
//        //默认分片上传为null
//        recorder(null)
//        zone(FixedZone.zone0)
//      }
//      .build()
//
//  private val uploader = UploadManager(config)
//
//    //todo test the upload and use the progress handler
//    fun upload(
//      builder: Builder,
//      upProgressHandler: (key: String, percent: Double) -> Unit
//    ) {
//      uploader.put(builder.data, builder.key, builder.token,
//          { key, info, response ->
//            if (info.isOK)
//              Log.d("qiniu", "ok")
//            else
//              Log.d("qiniu", "upload fails")
//          }, UploadOptions(null, null, false, upProgressHandler, null)
//      )
//  }

  class Builder(
     var data : File,
     var key:String ,
     var token:String)
  }