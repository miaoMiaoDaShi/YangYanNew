package com.yangyan.xxp.yangyannew.app

import android.app.Application
import android.content.Context
import android.support.v4.app.FragmentManager
import com.jess.arms.base.delegate.AppLifecycles
import com.jess.arms.di.module.GlobalConfigModule
import com.jess.arms.integration.ConfigModule

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description :  全局配置
 */
class GlobalConfiguration:ConfigModule {
    override fun injectFragmentLifecycle(context: Context?, lifecycles: MutableList<FragmentManager.FragmentLifecycleCallbacks>?) {


    }

    override fun applyOptions(context: Context?, builder: GlobalConfigModule.Builder?) {
    }

    override fun injectAppLifecycle(context: Context?, lifecycles: MutableList<AppLifecycles>?) {
    }

    override fun injectActivityLifecycle(context: Context?, lifecycles: MutableList<Application.ActivityLifecycleCallbacks>?) {
    }
}