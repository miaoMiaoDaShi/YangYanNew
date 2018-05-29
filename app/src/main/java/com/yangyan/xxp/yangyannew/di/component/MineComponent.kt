package com.yangyan.xxp.yangyannew.di.component

import dagger.Component

import com.jess.arms.di.component.AppComponent

import com.jess.arms.di.scope.FragmentScope
import com.yangyan.xxp.yangyannew.di.scope.FavoriteScope
import com.yangyan.xxp.yangyannew.di.module.FavoriteModule
import com.yangyan.xxp.yangyannew.di.module.MineModule
import com.yangyan.xxp.yangyannew.mvp.ui.fragment.MineFragment

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/23
 * Description :
 */
@FragmentScope
@FavoriteScope
@Component(modules = arrayOf(MineModule::class,FavoriteModule::class), dependencies = arrayOf(AppComponent::class))
interface MineComponent {
    fun inject(fragment: MineFragment)
}