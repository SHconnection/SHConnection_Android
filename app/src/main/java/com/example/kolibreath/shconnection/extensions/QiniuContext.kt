package com.example.kolibreath.shconnection.extensions

import android.util.Log
import com.qiniu.android.common.FixedZone
import com.qiniu.android.http.ResponseInfo
import com.qiniu.android.storage.Configuration
import com.qiniu.android.storage.UpCompletionHandler
import com.qiniu.android.storage.UpProgressHandler
import com.qiniu.android.storage.UploadManager
import com.qiniu.android.storage.UploadOptions
import org.json.JSONObject
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.File
import java.util.LinkedList
import javax.xml.transform.Templates

class QiniuExtension{


  companion object {

    private val config = Configuration.Builder().apply {
      chunkSize(512 * 1024)
      putThreshhold(1024 * 1024)
      connectTimeout(10)
      useHttps(true)
      responseTimeout(60)
      zone(FixedZone.zone0)
    }.build()!!

    private val uploadManager = UploadManager(config)

    private val token = Auth.create(
        "W7-6V7W1UycOh9jL9B_pWYSy6W-SwcURAYr_dBIZ", "jagQ78Gqv3m6VNnUetSJ7sgmT2aSJo-9iccFTDU_"
    )
        .uploadToken("android")

    private fun upload(
      data: File,
      name: String,
      completionHandler: (key: String, info: ResponseInfo, response: JSONObject) -> Unit
    ) {
      uploadManager.put(
          data,
          name,
          token,
          { key, info, response ->
            completionHandler(key,info, response)
          }, null
      )
    }

    /**
     * 将一个有文件路径的list 转换成一个包含名称和file object 的 hashmap 最后将这些list 包装为一个整体的Observable
     * 返回所有生成的外链的url
     * 这个是一个异步的过程
     */

    //todo 这个是一个异步的操作 如果上传所有东西写好了最好改成flatmap的形式
    var urls : LinkedList<String> = LinkedList()
    fun getUrls(pictures: ArrayList<String>) {
      val map = HashMap<String, File>()
      pictures.forEachIndexed { index, picture ->
        try {
          val file = File(picture)
          val name = "${System.currentTimeMillis()}_$index"
          map[name] = file
        } catch (e: Exception) {
          e.printStackTrace()
        }
      }
      val urls = LinkedList<String>()
      val observables = ArrayList<Observable<String>>()
      map.forEach {
        val data = it.value
        val name = it.key
        val observable = Observable.create(Observable.OnSubscribe<String> {
          upload(data = data, name = name) { key, _, _ ->
            run {
              Log.d("qiniu","fuck$key")
              it.onNext(key)
              it.onCompleted()
            }
          }
        })
        observables.add(observable)
      }

      Observable.merge(observables)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(object : Subscriber<String>() {
            override fun onNext(t: String?) {
              urls.add("http://http://ogbvujd8z.bkt.clouddn.com/$t")
              urls.forEach{
                Log.d("qiniu key", it)

              }
            }

            override fun onCompleted() {
              Log.d("qiniu", "上传完成")
            }

            override fun onError(e: Throwable?) {
              e?.printStackTrace()
            }
          })
    }
  }
}