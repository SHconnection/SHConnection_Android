package com.example.kolibreath.shconnection.extensions

import com.qiniu.android.common.FixedZone
import com.qiniu.android.http.ResponseInfo
import com.qiniu.android.storage.Configuration
import com.qiniu.android.storage.UpCompletionHandler
import com.qiniu.android.storage.UpProgressHandler
import com.qiniu.android.storage.UploadManager
import com.qiniu.android.storage.UploadOptions
import org.json.JSONObject
import java.io.File
import javax.xml.transform.Templates

object QiniuExtension{
  private val config = Configuration.Builder().apply {
    chunkSize(512*1024)
    putThreshhold(1024*1024)
    connectTimeout(10)
    useHttps(true)
    responseTimeout(60)
    zone(FixedZone.zone0)
  }.build()!!

  private val uploadManager = UploadManager(config)

  private val token = Auth.create("W7-6V7W1UycOh9jL9B_pWYSy6W-SwcURAYr_dBIZ","jagQ78Gqv3m6VNnUetSJ7sgmT2aSJo-9iccFTDU_")
      .uploadToken("android")

  fun upload(data: File,name:String,completionHandler:(key:String,info:ResponseInfo,response:JSONObject)->Unit,progressHandler:(percent:Double)->Unit){
    uploadManager.put(
        data,
        name,
        token,
        { key, info, response ->
      completionHandler.invoke(key,info,response)
        }
        ,UploadOptions(null,null,false, UpProgressHandler { key, percent ->
      progressHandler.invoke(percent)
    },null))
  }
}