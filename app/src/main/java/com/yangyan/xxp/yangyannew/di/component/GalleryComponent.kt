package com.yangyan.xxp.yangyannew.di.component

import dagger.Component

import com.jess.arms.di.component.AppComponent

import com.yangyan.xxp.yangyannew.di.module.MainModule

import com.jess.arms.di.scope.ActivityScope
import com.yangyan.xxp.yangyannew.di.module.GalleryModule
import com.yangyan.xxp.yangyannew.mvp.ui.activity.GalleryActivity
import com.yangyan.xxp.yangyannew.mvp.ui.activity.MainActivity

@ActivityScope
@Component(modules = arrayOf(GalleryModule::class), dependencies = arrayOf(AppComponent::class))
interface GalleryComponent {
    fun inject(activity: GalleryActivity)
}