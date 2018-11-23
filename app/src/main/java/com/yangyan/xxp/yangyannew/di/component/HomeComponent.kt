package com.yangyan.xxp.yangyannew.di.component

import dagger.Component

import com.jess.arms.di.component.AppComponent

import com.yangyan.xxp.yangyannew.di.module.MainModule

import com.jess.arms.di.scope.FragmentScope
import com.yangyan.xxp.yangyannew.di.module.HomeModule
import com.yangyan.xxp.yangyannew.mvp.ui.fragment.HomeFragment

@FragmentScope
@Component(modules = [HomeModule::class], dependencies = [AppComponent::class])
interface HomeComponent {
    fun inject(fragment: HomeFragment)
}