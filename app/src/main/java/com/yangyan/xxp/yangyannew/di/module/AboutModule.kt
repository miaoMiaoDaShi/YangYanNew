package com.yangyan.xxp.yangyannew.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.yangyan.xxp.yangyannew.mvp.contract.AboutContract
import com.yangyan.xxp.yangyannew.mvp.model.AboutModel


@Module
//构建AboutModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class AboutModule(private val view: AboutContract.View) {
    @ActivityScope
    @Provides
    fun provideAboutView(): AboutContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideAboutModel(model: AboutModel): AboutContract.Model {
        return model
    }
}
