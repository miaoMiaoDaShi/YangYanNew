package com.yangyan.xxp.yangyannew.di.component

import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.scope.ActivityScope
import com.yangyan.xxp.yangyannew.di.module.FavoriteImageListModule
import com.yangyan.xxp.yangyannew.di.module.FavoriteListModule
import com.yangyan.xxp.yangyannew.di.module.FavoriteModule
import com.yangyan.xxp.yangyannew.di.scope.FavoriteScope
import com.yangyan.xxp.yangyannew.mvp.contract.FavoriteImageListContract
import com.yangyan.xxp.yangyannew.mvp.ui.activity.FavoriteImageListActivity
import com.yangyan.xxp.yangyannew.mvp.ui.activity.FavoriteListActivity
import dagger.Component

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2019/1/5
 * Description : 收藏夹 列表
 */
@ActivityScope
@FavoriteScope
@Component(modules = [FavoriteListModule::class, FavoriteModule::class],dependencies = [YangYanComponent::class])
interface FavoriteListComponent {
    fun inject(activity: FavoriteListActivity)
}