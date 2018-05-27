package com.yangyan.xxp.yangyannew.di.module

import android.support.v7.widget.LinearLayoutManager
import com.jess.arms.di.scope.FragmentScope
import com.yangyan.xxp.yangyannew.mvp.contract.SearchContract
import com.yangyan.xxp.yangyannew.mvp.model.SearchModel
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.SearchAdapter
import dagger.Module
import dagger.Provides

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/26
 * Description :
 */
@Module
class SearchModule(val view:SearchContract.View) {

    @FragmentScope
    @Provides
    internal fun provideSearchView(): SearchContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    internal fun provideSearchModel(model: SearchModel): SearchContract.Model {
        return model
    }

    @FragmentScope
    @Provides
    internal fun provideLayoutManager() = LinearLayoutManager(view.getContext())
    @FragmentScope
    @Provides
    fun provideDatas() = mutableListOf<ImagesInfo>()

    @FragmentScope
    @Provides
    fun provideAdapter(datas: MutableList<ImagesInfo>) = SearchAdapter(datas)
}