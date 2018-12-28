package com.yangyan.xxp.yangyannew.di.module

import com.yangyan.xxp.yangyannew.di.scope.FavoriteScope
import com.yangyan.xxp.yangyannew.mvp.model.entity.FavoriteInfo
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.HomeAdapter
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.MineFavoriteAdapter
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/29
 * Description : 收藏
 */
@Module
class FavoriteModule {
    @FavoriteScope
    @Provides
    fun provideFavoriteDatas() = mutableListOf<FavoriteInfo>()

    @FavoriteScope
    @Provides
    fun provideFavoriteAdapter(datas: MutableList<FavoriteInfo>) = MineFavoriteAdapter(datas)

    @FavoriteScope
    @Provides
    @Named("FavoriteImagesDatas")
    internal fun provideImagesDatas() = mutableListOf<ImagesInfo>()

    @FavoriteScope
    @Provides
    @Named("FavoriteImagesAdapter")
    internal fun provideImagesAdapter(@Named("FavoriteImagesDatas") datas: MutableList<ImagesInfo>) = HomeAdapter(datas)

}