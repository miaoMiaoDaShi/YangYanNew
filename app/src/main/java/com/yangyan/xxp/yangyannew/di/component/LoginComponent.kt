package com.yangyan.xxp.yangyannew.di.component

import dagger.Component

import com.jess.arms.di.component.AppComponent

import com.yangyan.xxp.yangyannew.di.module.MainModule

import com.jess.arms.di.scope.ActivityScope
import com.yangyan.xxp.yangyannew.di.module.LoginModule
import com.yangyan.xxp.yangyannew.mvp.ui.activity.LoginActivity
import com.yangyan.xxp.yangyannew.mvp.ui.activity.MainActivity

@ActivityScope
@Component(modules = [LoginModule::class], dependencies = [AppComponent::class])
interface LoginComponent {
    fun inject(activity: LoginActivity)
}