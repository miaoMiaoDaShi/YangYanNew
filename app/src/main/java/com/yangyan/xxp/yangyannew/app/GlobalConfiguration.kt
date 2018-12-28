package com.yangyan.xxp.yangyannew.app

import android.app.Application
import android.content.Context
import android.support.v4.app.FragmentManager
import com.jess.arms.base.delegate.AppLifecycles
import com.jess.arms.di.module.GlobalConfigModule
import com.jess.arms.http.log.RequestInterceptor
import com.jess.arms.integration.ConfigModule
import com.yangyan.xxp.yangyannew.BuildConfig
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description :  全局配置
 */
class GlobalConfiguration : ConfigModule {
    override fun injectFragmentLifecycle(context: Context?, lifecycles: MutableList<FragmentManager.FragmentLifecycleCallbacks>?) {


    }

    override fun applyOptions(context: Context, builder: GlobalConfigModule.Builder) {
        builder.apply {
            if (!BuildConfig.LOG_DEBUG) { //Release 时,让框架不再打印 Http 请求和响应的信息
                printHttpLogLevel(RequestInterceptor.Level.NONE)
            }

            baseurl(Constant.API_HOST)
            imageLoaderStrategy(YangYanGlideImageLoaderStrategy())
            responseErrorListener(ResponseErrorListenerImpl())

            retrofitConfiguration { context, builder ->
                builder.addConverterFactory(ScalarsConverterFactory.create())
            }
//                    .okhttpConfiguration { context, builder ->
//                        builder.sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getTrustManager()) //支持 Https,详情请百度
//                        builder.writeTimeout(30, TimeUnit.SECONDS)
//                        builder.hostnameVerifier(SSLSocketClient.getHostnameVerifier())
//                    }
            gsonConfiguration { context, builder ->
                builder.serializeNulls()
                        .enableComplexMapKeySerialization()
            }
            globalHttpHandler(GlobalHttpHandlerImpl(context))
        }

    }

    override fun injectAppLifecycle(context: Context?, lifecycles: MutableList<AppLifecycles>?) {
        lifecycles?.add(AppLifecyclesImpl())
    }

    override fun injectActivityLifecycle(context: Context?, lifecycles: MutableList<Application.ActivityLifecycleCallbacks>?) {
        lifecycles?.add(ActivityLifecycleImpl())
    }
}