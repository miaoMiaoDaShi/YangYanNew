package com.yangyan.xxp.yangyannew.di.module

import android.support.v7.widget.LinearLayoutManager
import com.jess.arms.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

import com.yangyan.xxp.yangyannew.mvp.contract.MineContract
import com.yangyan.xxp.yangyannew.mvp.model.MineModel
import com.yangyan.xxp.yangyannew.mvp.model.entity.CollectInfo
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.MineCollectAdapter

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/23
 * Description :
 */
@Module
class MineModule
(private val view: MineContract.View) {

    @FragmentScope
    @Provides
    internal fun provideMineView(): MineContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    internal fun provideMineModel(model: MineModel): MineContract.Model {
        return model
    }
    @FragmentScope
    @Provides
    internal fun provideLayoutManager() = LinearLayoutManager(view.getContext())

    @FragmentScope
    @Provides
    internal fun provideDatas() = mutableListOf<CollectInfo>()

    @FragmentScope
    @Provides
    internal fun provideAdapter(datas: MutableList<CollectInfo>) = MineCollectAdapter(datas)
}

