package com.yangyan.xxp.yangyannew.di.component

import dagger.Component

import com.jess.arms.di.component.AppComponent

import com.yangyan.xxp.yangyannew.di.module.MainModule

import com.jess.arms.di.scope.FragmentScope
import com.yangyan.xxp.yangyannew.di.module.HomeModule
import com.yangyan.xxp.yangyannew.mvp.ui.fragment.HomeFragment

@FragmentScope
@Component(modules = arrayOf(HomeModule::class), dependencies = arrayOf(AppComponent::class))
interface HomeComponent {
    fun inject(fragment: HomeFragment)
}