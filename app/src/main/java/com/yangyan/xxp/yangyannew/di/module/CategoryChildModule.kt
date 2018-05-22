package com.yangyan.xxp.yangyannew.di.module

import android.support.v7.widget.LinearLayoutManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.di.scope.FragmentScope
import com.yangyan.xxp.yangyannew.mvp.contract.CategoryChildContract
import com.yangyan.xxp.yangyannew.mvp.contract.HomeContract
import com.yangyan.xxp.yangyannew.mvp.contract.MainContract
import com.yangyan.xxp.yangyannew.mvp.model.CategoryChildModel
import com.yangyan.xxp.yangyannew.mvp.model.HomeModel
import com.yangyan.xxp.yangyannew.mvp.model.MainModel
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.HomeAdapter
import dagger.Module
import dagger.Provides

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/22
 * Description :分类子页面
 */
@Module
class CategoryChildModule
constructor(private val view: CategoryChildContract.View) {
    @FragmentScope
    @Provides
    internal fun provideCategoryChildView(): CategoryChildContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    internal fun provideCategoryChildModel(model: CategoryChildModel): CategoryChildContract.Model {
        return model
    }

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