package com.yangyan.xxp.yangyannew.di.component

import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.scope.ActivityScope
import com.yangyan.xxp.yangyannew.di.scope.FavoriteScope
import com.yangyan.xxp.yangyannew.di.module.AddFavoriteModule
import com.yangyan.xxp.yangyannew.di.module.FavoriteModule
import com.yangyan.xxp.yangyannew.mvp.ui.activity.AddFavoriteActivity
import dagger.Component

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/28
 * Description : 添加收藏夹
 */
@ActivityScope
@FavoriteScope
@Component(modules = arrayOf(AddFavoriteModule::class,FavoriteModule::class),dependencies = arrayOf(AppComponent::class))
interface AddFavoriteComponent {
    fun inject(activity:AddFavoriteActivity)
}