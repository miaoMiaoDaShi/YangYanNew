package com.yangyan.xxp.yangyannew.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.yangyan.xxp.yangyannew.di.module.AboutModule

import com.jess.arms.di.scope.ActivityScope
import com.yangyan.xxp.yangyannew.mvp.ui.activity.AboutActivity

@ActivityScope
@Component(modules = arrayOf(AboutModule::class), dependencies = arrayOf(AppComponent::class))
interface AboutComponent {
    fun inject(activity: AboutActivity)
}
