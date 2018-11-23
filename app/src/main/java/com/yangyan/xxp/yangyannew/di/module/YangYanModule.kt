package com.yangyan.xxp.yangyannew.di.module

import android.app.Application
import com.tencent.cos.xml.CosXmlServiceConfig
import com.tencent.cos.xml.CosXmlSimpleService
import com.tencent.qcloud.core.auth.ShortTimeCredentialProvider
import com.yangyan.xxp.yangyannew.di.scope.YangYanScope
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/11/23
 * Description :
 */
@Module
class YangYanModule {

    /**
     * 腾讯对象云服务
     */
    @YangYanScope
    @Provides
    fun provideCosXmlService(application: Application): CosXmlSimpleService {
        return CosXmlSimpleService(application, CosXmlServiceConfig.Builder()
                .setAppidAndRegion("1252246683", "ap-chengdu")
                .builder(), ShortTimeCredentialProvider("AKIDSWP89Vrrywz52xRQPHExaN5fAydKYaYZ",
                "56xlT3ZwJmLvMtMnGdq01t5BjDan1KZ9", 300))
    }
}