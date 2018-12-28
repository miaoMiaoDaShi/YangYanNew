package com.yangyan.xxp.yangyannew.di.module

import android.support.v7.widget.LinearLayoutManager
import com.jess.arms.di.scope.FragmentScope
import com.yangyan.xxp.yangyannew.mvp.contract.HomeContract
import com.yangyan.xxp.yangyannew.mvp.model.HomeModel
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.HomeAdapter
import com.yangyan.xxp.yangyannew.mvp.ui.fragment.SearchFragment
import dagger.Module
import dagger.Provides

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/22
 * Description :
 */
@Module
class HomeModule
constructor(private val view: HomeContract.View) {
    @FragmentScope
    @Provides
    internal fun provideHomeView(): HomeContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    internal fun provideHomeModel(model: HomeModel): HomeContract.Model {
        return model
    }

    @FragmentScope
    @Provides
    fun provideSearchFragment() = SearchFragment.newInstance()

    @FragmentScope
    @Provides
    internal fun provideLayoutManager() = LinearLayoutManager(view.getContext())

    @FragmentScope
    @Provides
    internal fun provideDatas() = mutableListOf<ImagesInfo>()

    @FragmentScope
    @Provides
    internal fun provideAdapter(datas: MutableList<ImagesInfo>) = HomeAdapter(datas)
}