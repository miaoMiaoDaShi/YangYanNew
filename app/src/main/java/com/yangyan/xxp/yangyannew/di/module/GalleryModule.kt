package com.yangyan.xxp.yangyannew.di.module

import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import com.jess.arms.di.scope.ActivityScope
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.mvp.contract.GalleryContract
import com.yangyan.xxp.yangyannew.mvp.contract.ImageCollectionContract
import com.yangyan.xxp.yangyannew.mvp.model.GalleryModel
import com.yangyan.xxp.yangyannew.mvp.model.ImageCollectionModel
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.GalleryPageAdapter
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.ImageCollectionAdapter
import dagger.Module
import dagger.Provides

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/25
 * Description :
 */
@Module
class GalleryModule(val view: GalleryContract.View) {
    @ActivityScope
    @Provides
    internal fun provideGalleryView(): GalleryContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    internal fun provideGalleryModel(model: GalleryModel): GalleryContract.Model {
        return model
    }


    @ActivityScope
    @Provides
    internal fun provideImageCollectionModel(model: ImageCollectionModel): ImageCollectionContract.Model {
        return model
    }

    @ActivityScope
    @Provides
    internal fun provideDatas() = mutableListOf<ImagesInfo>()

    @ActivityScope
    @Provides
    internal fun provideAdapter(datas: MutableList<ImagesInfo>) = GalleryPageAdapter(datas)
}