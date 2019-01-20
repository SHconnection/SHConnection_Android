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


const val appid :String= "1258093397"
const val region :String = "ap-chengdu"



private fun upload(context:Context,absSrcPath:String){

    fun createCosXmlConfig ():CosXmlServiceConfig{
        return CosXmlServiceConfig.Builder()
                .setAppidAndRegion(appid, region)
                .setDebuggable(true)
                .builder()
    }

    val secretId = "AKIDXrztayYhhTzazC42c2EF6kEXXMwNkIp7"
    val secretKey = "C9sM5YsxrZ8SdZGkzyiDTTHpCKDrFPK3"

    //使用永久密钥进行授权
    val credentialProvider = ShortTimeCredentialProvider(secretId,secretKey,300)
    val cosXmlService = CosXmlSimpleService(context,createCosXmlConfig(),credentialProvider)

    //upload Id 分片上传的id 设置为null
    val upLoadId = null

    //使用分片上传
    val transferConfig = TransferConfig.Builder()
            .setDividsionForCopy(5*1024*1024)
            .setSliceSizeForCopy(5*1024*1024)
            .setDivisionForUpload(2*1024*1024)
            .build()

    val transferManager = TransferManager(cosXmlService,transferConfig)
    val bucket = "kolibreath-1258093397"
    val cosPath = System.currentTimeMillis().toString()

    val uploadTask = transferManager.upload(bucket,cosPath,absSrcPath,upLoadId)
    //上传进度回调
    uploadTask.setCosXmlResultListener(object : CosXmlResultListener {
        override fun onSuccess(request: CosXmlRequest, result: CosXmlResult) {
            Log.d("TEST", "Success: " + result.httpMessage)
        }

        override fun onFail(request: CosXmlRequest, exception: CosXmlClientException?, serviceException: CosXmlServiceException) {
            Log.d("TEST", "Failed: " + (exception?.toString() ?: serviceException.message))
        }
    })
}
