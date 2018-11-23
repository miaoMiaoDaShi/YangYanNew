package com.yangyan.xxp.yangyannew.di.component

import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.scope.ActivityScope
import com.yangyan.xxp.yangyannew.di.module.FavoriteImageListModule
import com.yangyan.xxp.yangyannew.di.module.FavoriteModule
import com.yangyan.xxp.yangyannew.di.scope.FavoriteScope
import com.yangyan.xxp.yangyannew.mvp.contract.FavoriteImageListContract
import com.yangyan.xxp.yangyannew.mvp.ui.activity.FavoriteImageListActivity
import dagger.Component

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/29
 * Description : 收藏夹 点进去的页面
 */
@ActivityScope
@FavoriteScope
@Component(modules = [FavoriteImageListModule::class, FavoriteModule::class],dependencies = [YangYanComponent::class])
interface FavoriteImageListComponent {
    fun inject(activity:FavoriteImageListActivity)
}