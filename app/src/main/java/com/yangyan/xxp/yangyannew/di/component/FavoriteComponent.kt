package com.yangyan.xxp.yangyannew.di.component

import com.yangyan.xxp.yangyannew.di.FavoriteScope
import com.yangyan.xxp.yangyannew.di.module.FavoriteModule
import com.yangyan.xxp.yangyannew.di.module.MineModule
import com.yangyan.xxp.yangyannew.mvp.model.entity.FavoriteInfo
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.MineFavoriteAdapter
import dagger.Component

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/29
 * Description :
 */
//@FavoriteScope
//@Component(modules = arrayOf(FavoriteModule::class))
//interface FavoriteComponent {
//    fun datas(): MutableList<FavoriteInfo>
//    fun adapter(): MineFavoriteAdapter
//
//    fun  addSub(mineModule: MineModule):MineComponent
//}