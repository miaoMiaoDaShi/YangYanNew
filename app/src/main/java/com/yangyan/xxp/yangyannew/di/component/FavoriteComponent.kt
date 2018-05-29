package com.yangyan.xxp.yangyannew.di.component

import com.yangyan.xxp.yangyannew.di.module.FavoriteModule
import com.yangyan.xxp.yangyannew.mvp.model.entity.FavoriteInfo
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.MineFavoriteAdapter
import dagger.Component
import javax.inject.Singleton

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/29
 * Description :
 */
@Component(modules = arrayOf(FavoriteModule::class))
interface FavoriteComponent {
    fun datas(): MutableList<FavoriteInfo>
    fun adapter(): MineFavoriteAdapter
}