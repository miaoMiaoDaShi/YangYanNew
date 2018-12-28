package com.yangyan.xxp.yangyannew.mvp.ui.service

import android.app.IntentService
import android.content.Intent
import android.os.Environment
import com.jess.arms.utils.ArmsUtils
import com.yangyan.xxp.yangyannew.app.Constant
import com.yangyan.xxp.yangyannew.exception.DownloadException
import com.yangyan.xxp.yangyannew.mvp.model.service.CommonService
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import okhttp3.ResponseBody
import timber.log.Timber
import java.io.*

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/24
 * Description : 很简单的一个下载服务  主要拿来下图片的
 *  为啥用IntentService 下完就不用管了 百度为什么
 */
class DownloadService : IntentService("YangYanDownload") {
    lateinit var mInputSteam: InputStream
    lateinit var mOutputStream: OutputStream
    override fun onHandleIntent(intent: Intent) {
        val fileName = intent.getStringExtra("name") ?: "${System.currentTimeMillis()}.jpg"
        val downloadUrl = intent.getStringExtra("downloadUrl")
        if (downloadUrl.isNullOrEmpty()) {
            throw DownloadException("连接地址不能为空")
        }
        val appComment = ArmsUtils.obtainAppComponentFromContext(applicationContext)
        appComment.repositoryManager().obtainRetrofitService(CommonService::class.java)
                .download(downloadUrl)
                .subscribe(object : ErrorHandleSubscriber<ResponseBody>(appComment.rxErrorHandler()) {
                    override fun onNext(t: ResponseBody) {
                        //保存文件到sd卡
                        try {
                            val filePath = Constant.SPLASH_LOCAL_PATH
                            val dir = File(filePath)
                            if (!dir.exists()) {
                                dir.mkdirs()
                            }


                            val file = File(filePath, fileName)

                            val byteArray = ByteArray(2048)
                            //val fileSize = t.contentLength()
                            mInputSteam = t.byteStream()
                            mOutputStream = FileOutputStream(file)
                            while (true) {
                                val read = mInputSteam.read(byteArray)
                                if (read == -1) break
                                mOutputStream.write(byteArray, 0, read)
                            }

                            mOutputStream.flush()


                        } catch (e: Exception) {
                            Timber.e(e)
                            throw DownloadException(e.toString())
                        }
                    }

                })
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            mInputSteam.close()
            mOutputStream.close()
        } catch (e: Exception) {
        }
    }
}