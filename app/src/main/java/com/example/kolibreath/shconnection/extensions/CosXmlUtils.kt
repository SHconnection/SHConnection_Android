package com.example.kolibreath.shconnection.extensions

import android.content.Context
import android.util.Log
import com.tencent.cos.xml.CosXmlServiceConfig
import com.tencent.cos.xml.CosXmlSimpleService
import com.tencent.cos.xml.exception.CosXmlClientException
import com.tencent.cos.xml.exception.CosXmlServiceException
import com.tencent.cos.xml.listener.CosXmlResultListener
import com.tencent.cos.xml.model.CosXmlRequest
import com.tencent.cos.xml.model.CosXmlResult
import com.tencent.cos.xml.transfer.TransferConfig
import com.tencent.cos.xml.transfer.TransferManager
import com.tencent.qcloud.core.auth.ShortTimeCredentialProvider
import java.util.*
import kotlin.collections.ArrayList


const val appid :String= "1258093397"
const val region :String = "ap-chengdu"


class CosXmlUtils(context: Context) {


    fun createCosXmlConfig(): CosXmlServiceConfig {
        return CosXmlServiceConfig.Builder()
                .setAppidAndRegion(appid, region)
                .setDebuggable(true)
                .builder()
    }

    val secretId = "AKIDXrztayYhhTzazC42c2EF6kEXXMwNkIp7"
    val secretKey = "C9sM5YsxrZ8SdZGkzyiDTTHpCKDrFPK3"

    //使用永久密钥进行授权
    val credentialProvider = ShortTimeCredentialProvider(secretId, secretKey, 300)
    val cosXmlService = CosXmlSimpleService(context, createCosXmlConfig(), credentialProvider)

    //upload Id 分片上传的id 设置为null
    val upLoadId = null

    //使用分片上传
    val transferConfig = TransferConfig.Builder()
            .setDividsionForCopy(5 * 1024 * 1024)
            .setSliceSizeForCopy(5 * 1024 * 1024)
            .setDivisionForUpload(2 * 1024 * 1024)
            .build()

    val transferManager = TransferManager(cosXmlService, transferConfig)
    val bucket = "muxixyz-1258093397"
    val cosPath = System.currentTimeMillis().toString() + ".jpeg"





    fun cosUpload(absSrcPaths:ArrayList<String>):rx.Observable<MutableList<String>>{

        return rx.Observable.from(absSrcPaths)
                .flatMap { absPath ->
                    val observable = rx.Observable.create(rx.Observable.OnSubscribe<String> {
                        val uploadTask = transferManager.upload(bucket,cosPath,absPath,upLoadId)
                        uploadTask.setCosXmlResultListener(object : CosXmlResultListener {
                            //上传进度回调
                            override fun onSuccess(request: CosXmlRequest, result: CosXmlResult) {
                                it.onNext(result.accessUrl.replace("cos","cos-website"))
                                it.onCompleted()
                            }

                            override fun onFail(request: CosXmlRequest, exception: CosXmlClientException?, serviceException: CosXmlServiceException) {
                                Log.d("TEST", "Failed: " + (exception?.toString() ?: serviceException.message))
                            }
                        })
                    })
                    observable
                }.toList()


    }


}