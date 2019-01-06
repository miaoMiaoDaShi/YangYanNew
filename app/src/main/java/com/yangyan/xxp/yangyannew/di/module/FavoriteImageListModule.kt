package com.yangyan.xxp.yangyannew.di.module

import android.support.v7.widget.LinearLayoutManager
import com.jess.arms.di.scope.ActivityScope
import com.yangyan.xxp.yangyannew.mvp.contract.FavoriteImageListContract
import com.yangyan.xxp.yangyannew.mvp.model.FavoriteImageListModel
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.HomeAdapter
import com.yangyan.xxp.yangyannew.mvp.ui.dialog.WarningDialog
import dagger.Module
import dagger.Provides

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/29
 * Description :
 */
@Module
class FavoriteImageListModule
constructor(private val view: FavoriteImageListContract.View) {
    @ActivityScope
    @Provides
    internal fun provideFavoriteImageListView(): FavoriteImageListContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    internal fun provideFavoriteImageListModel(model: FavoriteImageListModel): FavoriteImageListContract.Model {
        return model
    }

    @ActivityScope
    @Provides
    internal fun provideLayoutManager() = LinearLayoutManager(view.getContext())

    @ActivityScope
    @Provides
    internal fun provideDatas() = mutableListOf<ImagesInfo>()

    @ActivityScope
    @Provides
    internal fun provideAdapter(datas: MutableList<ImagesInfo>) = HomeAdapter(datas)

    @ActivityScope
    @Provides
    internal fun provideWarningDialog() = WarningDialog()
}