package com.yangyan.xxp.yangyannew.di.module

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.di.scope.FragmentScope
import com.yangyan.xxp.yangyannew.mvp.contract.MineContract
import com.yangyan.xxp.yangyannew.mvp.contract.SignUpContract
import com.yangyan.xxp.yangyannew.mvp.model.MineModel
import com.yangyan.xxp.yangyannew.mvp.model.SignUpModel
import dagger.Module
import dagger.Provides

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/23
 * Description :注册
 */
@Module
class SignUpModule(val view:SignUpContract.View) {
    @ActivityScope
    @Provides
    internal fun provideSignUpView(): SignUpContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    internal fun provideSignUpModel(model: SignUpModel): SignUpContract.Model {
        return model
    }
}