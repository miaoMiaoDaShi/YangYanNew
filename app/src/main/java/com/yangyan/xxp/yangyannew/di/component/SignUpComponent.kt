package com.yangyan.xxp.yangyannew.di.component

import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.scope.ActivityScope
import com.yangyan.xxp.yangyannew.di.module.SignUpModule
import com.yangyan.xxp.yangyannew.mvp.ui.activity.SignUpActivity
import dagger.Component

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/23
 * Description :注册
 */
@ActivityScope
@Component(modules = [SignUpModule::class], dependencies = [AppComponent::class])
interface SignUpComponent {
    fun inject(activity: SignUpActivity)
}