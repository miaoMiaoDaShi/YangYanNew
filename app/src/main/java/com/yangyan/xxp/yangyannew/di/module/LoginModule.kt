package com.yangyan.xxp.yangyannew.di.module

import com.jess.arms.di.scope.ActivityScope
import com.yangyan.xxp.yangyannew.mvp.contract.LoginContract

import dagger.Module
import dagger.Provides

import com.yangyan.xxp.yangyannew.mvp.contract.MainContract
import com.yangyan.xxp.yangyannew.mvp.model.LoginModel
import com.yangyan.xxp.yangyannew.mvp.model.MainModel
import com.yangyan.xxp.yangyannew.mvp.ui.fragment.CategoryFragment
import com.yangyan.xxp.yangyannew.mvp.ui.fragment.HomeFragment
import com.yangyan.xxp.yangyannew.mvp.ui.fragment.MineFragment

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/23
 * Description :
 */
@Module
class LoginModule
(private val view: LoginContract.View) {

    @ActivityScope
    @Provides
    internal fun provideLoginView(): LoginContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    internal fun provideLoginModel(model: LoginModel): LoginContract.Model {
        return model
    }
}

