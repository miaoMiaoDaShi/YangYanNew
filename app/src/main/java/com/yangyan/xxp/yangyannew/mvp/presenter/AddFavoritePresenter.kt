package com.yangyan.xxp.yangyannew.mvp.presenter

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.yangyan.xxp.yangyannew.mvp.contract.AddFavoriteContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.FavoriteInfo
import es.dmoral.toasty.Toasty
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/28
 * Description : 收藏夹
 */
@ActivityScope
class AddFavoritePresenter @Inject
constructor(model: AddFavoriteContract.Model, view: AddFavoriteContract.View)
    : FavoritePresenter<AddFavoriteContract.Model, AddFavoriteContract.View>(model, view) {




}