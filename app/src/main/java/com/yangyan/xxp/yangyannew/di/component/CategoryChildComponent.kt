package com.yangyan.xxp.yangyannew.di.component

import dagger.Component

import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.scope.FragmentScope
import com.yangyan.xxp.yangyannew.di.module.CategoryChildModule

import com.yangyan.xxp.yangyannew.di.module.MainModule

import com.yangyan.xxp.yangyannew.mvp.ui.fragment.CategoryChildFragment
import com.yangyan.xxp.yangyannew.mvp.ui.fragment.CategoryFragment
import com.yangyan.xxp.yangyannew.mvp.ui.fragment.HomeFragment

@FragmentScope
@Component(modules = [CategoryChildModule::class], dependencies = [AppComponent::class])
interface CategoryChildComponent {
    fun inject(fragment: CategoryChildFragment)
}