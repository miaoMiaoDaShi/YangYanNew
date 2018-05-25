package com.yangyan.xxp.yangyannew.di.component

import dagger.Component

import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.scope.ActivityScope

import com.yangyan.xxp.yangyannew.di.module.MainModule

import com.jess.arms.di.scope.FragmentScope
import com.yangyan.xxp.yangyannew.di.module.HomeModule
import com.yangyan.xxp.yangyannew.di.module.ImageCollectionModule
import com.yangyan.xxp.yangyannew.mvp.ui.activity.ImageCollectionActivity
import com.yangyan.xxp.yangyannew.mvp.ui.fragment.HomeFragment

@ActivityScope
@Component(modules = arrayOf(ImageCollectionModule::class), dependencies = arrayOf(AppComponent::class))
interface ImageCollectionComponent {
    fun inject(activity: ImageCollectionActivity)
}