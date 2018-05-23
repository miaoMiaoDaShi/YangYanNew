package com.yangyan.xxp.yangyannew.di.component

import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.scope.ActivityScope
import com.yangyan.xxp.yangyannew.di.module.SignUpModule
import com.yangyan.xxp.yangyannew.di.module.SplashModule
import com.yangyan.xxp.yangyannew.mvp.ui.activity.SplashActivity
import dagger.Component

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/23
 * Description :启动页面的
 */
@ActivityScope
@Component(modules = arrayOf(SplashModule::class),dependencies = arrayOf(AppComponent::class))
interface SplashComponent {
    fun inject(activity:SplashActivity)
}