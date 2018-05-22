package com.yangyan.xxp.yangyannew.di.component

import dagger.Component

import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.di.scope.FragmentScope
import com.yangyan.xxp.yangyannew.di.module.CategoryChildModule
import com.yangyan.xxp.yangyannew.di.module.CategoryModule
import com.yangyan.xxp.yangyannew.di.module.HomeModule
import com.yangyan.xxp.yangyannew.mvp.ui.fragment.CategoryChildFragment


import com.yangyan.xxp.yangyannew.mvp.ui.fragment.CategoryFragment
import com.yangyan.xxp.yangyannew.mvp.ui.fragment.HomeFragment
import javax.inject.Singleton

@FragmentScope
@Component(modules = arrayOf(CategoryModule::class), dependencies = arrayOf(AppComponent::class))
interface CategoryComponent {
    fun inject(fragment: CategoryFragment)
}