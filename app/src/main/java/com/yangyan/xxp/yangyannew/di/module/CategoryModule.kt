package com.yangyan.xxp.yangyannew.di.module

import android.support.v7.widget.LinearLayoutManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.di.scope.FragmentScope
import com.yangyan.xxp.yangyannew.mvp.contract.CategoryContract
import com.yangyan.xxp.yangyannew.mvp.contract.HomeContract
import com.yangyan.xxp.yangyannew.mvp.contract.MainContract
import com.yangyan.xxp.yangyannew.mvp.model.CategoryModel
import com.yangyan.xxp.yangyannew.mvp.model.HomeModel
import com.yangyan.xxp.yangyannew.mvp.model.MainModel
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.CategoryFragmentPageAdapter
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.HomeAdapter
import dagger.Module
import dagger.Provides
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/22
 * Description :
 */
@Module
class CategoryModule
constructor(private val view: CategoryContract.View) {
    @FragmentScope
    @Provides
    internal fun provideCategoryView(): CategoryContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    internal fun provideCategoryModel(model: CategoryModel): CategoryContract.Model {
        return model
    }

    @FragmentScope
    @Provides
    internal fun providePageAdapter() = CategoryFragmentPageAdapter(view.getSupportFragmentManager())
}