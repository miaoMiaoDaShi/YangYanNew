package com.yangyan.xxp.yangyannew.di.module

import com.yangyan.xxp.yangyannew.mvp.model.entity.FavoriteInfo
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.MineFavoriteAdapter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/29
 * Description : 收藏
 */
@Module
class FavoriteModule {
    @Provides
     fun provideDatas() = mutableListOf<FavoriteInfo>()

    @Provides
     fun provideAdapter(datas: MutableList<FavoriteInfo>) = MineFavoriteAdapter(datas)
}