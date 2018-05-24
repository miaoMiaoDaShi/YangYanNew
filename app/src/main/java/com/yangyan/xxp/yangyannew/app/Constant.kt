package com.yangyan.xxp.yangyannew.app

import android.os.Environment
import java.io.File

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/22
 * Description :
 */
object Constant {
    val API_HOST = "http://m.xxxiao.com/"
    val BMOB_APPLICATION_KEY = "39a0df90ea32771643ead11e26508d90"
    val SPLASH_IMAGE_URL = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/1/1"
    //splash图片的位置
    val SPLASH_LOCAL_PATH = Environment.getExternalStorageDirectory()
            .absolutePath + File.separator + "YangYan/image/"
    //splash图片的名字
    val SPLASH_LOCAL_NAME = "splash.jpg"
}