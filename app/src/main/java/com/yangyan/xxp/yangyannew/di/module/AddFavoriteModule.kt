package com.yangyan.xxp.yangyannew.di.module

import com.jess.arms.di.scope.ActivityScope
import com.yangyan.xxp.yangyannew.mvp.contract.AddFavoriteContract
import com.yangyan.xxp.yangyannew.mvp.contract.MainContract
import com.yangyan.xxp.yangyannew.mvp.model.AddFavoriteModel
import com.yangyan.xxp.yangyannew.mvp.model.MainModel
import dagger.Module
import dagger.Provides

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/28
 * Description :
 */
@Module
class AddFavoriteModule(val view:AddFavoriteContract.View) {
    @ActivityScope
    @Provides
    internal fun provideAddFavoriteView(): AddFavoriteContract.View {
        return view
    }

    @ActivityScope
    @Provides
    internal fun provideAddFavoriteModel(model: AddFavoriteModel): AddFavoriteContract.Model {
        return model
    }
}