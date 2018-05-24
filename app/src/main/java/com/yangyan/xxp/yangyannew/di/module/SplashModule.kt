package com.yangyan.xxp.yangyannew.di.module

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.di.scope.FragmentScope
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yangyan.xxp.yangyannew.mvp.contract.MineContract
import com.yangyan.xxp.yangyannew.mvp.contract.SignUpContract
import com.yangyan.xxp.yangyannew.mvp.contract.SplashContract
import com.yangyan.xxp.yangyannew.mvp.model.MineModel
import com.yangyan.xxp.yangyannew.mvp.model.SignUpModel
import com.yangyan.xxp.yangyannew.mvp.model.SplashModel
import dagger.Module
import dagger.Provides

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/23
 * Description :启动
 */
@Module
class SplashModule(val view: SplashContract.View) {
    @ActivityScope
    @Provides
    internal fun provideSplashView(): SplashContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    internal fun provideSplashModel(model: SplashModel): SplashContract.Model {
        return model
    }

    @ActivityScope
    @Provides
    internal fun provideRxPermission() = RxPermissions(view.getActivity())

}