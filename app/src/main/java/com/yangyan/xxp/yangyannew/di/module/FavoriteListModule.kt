package com.yangyan.xxp.yangyannew.di.module

import android.support.v7.widget.LinearLayoutManager
import com.jess.arms.di.scope.ActivityScope
import com.yangyan.xxp.yangyannew.mvp.contract.FavoriteContract
import com.yangyan.xxp.yangyannew.mvp.contract.FavoriteImageListContract
import com.yangyan.xxp.yangyannew.mvp.contract.FavoriteListContract
import com.yangyan.xxp.yangyannew.mvp.model.FavoriteImageListModel
import com.yangyan.xxp.yangyannew.mvp.model.FavoriteListModel
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.HomeAdapter
import com.yangyan.xxp.yangyannew.mvp.ui.dialog.WarningDialog
import dagger.Module
import dagger.Provides

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2019/1/5
 * Description :
 */
@Module
class FavoriteListModule
constructor(private val view: FavoriteListContract.View) {
    @ActivityScope
    @Provides
    internal fun provideFavoriteListView(): FavoriteListContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    internal fun provideFavoriteListModel(model: FavoriteListModel): FavoriteListContract.Model {
        return model
    }

    @ActivityScope
    @Provides
    internal fun provideLayoutManager() = LinearLayoutManager(view.getContext())

    @ActivityScope
    @Provides
    internal fun provideWarningDialog() = WarningDialog()
}