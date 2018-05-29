package com.yangyan.xxp.yangyannew.di.module

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.di.scope.FragmentScope
import com.yangyan.xxp.yangyannew.mvp.contract.HomeContract
import com.yangyan.xxp.yangyannew.mvp.contract.ImageCollectionContract
import com.yangyan.xxp.yangyannew.mvp.contract.MainContract
import com.yangyan.xxp.yangyannew.mvp.model.HomeModel
import com.yangyan.xxp.yangyannew.mvp.model.ImageCollectionModel
import com.yangyan.xxp.yangyannew.mvp.model.MainModel
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.HomeAdapter
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.ImageCollectionAdapter
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/22
 * Description :
 */
@Module
class ImageCollectionModule
constructor(private val view: ImageCollectionContract.View) {
    @ActivityScope
    @Provides
    internal fun provideImageCollectionView(): ImageCollectionContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    internal fun provideImageCollectionModel(model: ImageCollectionModel): ImageCollectionContract.Model {
        return model
    }

    @ActivityScope
    @Provides
    internal fun provideLayoutManager() = GridLayoutManager(view.getContext(),2)

    @ActivityScope
    @Provides
    internal fun provideLinearLayoutManager() = LinearLayoutManager(view.getContext())

    @ActivityScope
    @Provides
    @Named("ImageCollectionImagesDatas")
    internal fun provideDatas() = mutableListOf<ImagesInfo>()

    @ActivityScope
    @Provides
    @Named("ImageCollectionImagesAdapter")
    internal fun provideAdapter( @Named("ImageCollectionImagesDatas") datas: MutableList<ImagesInfo>) = ImageCollectionAdapter(datas)
}