package com.yangyan.xxp.yangyannew.mvp.presenter

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.utils.RxLifecycleUtils
import com.yangyan.xxp.yangyannew.mvp.contract.FavoriteImageListContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.FavoriteInfo
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import es.dmoral.toasty.Toasty
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/29
 * Description :
 */
@ActivityScope
class FavoriteImageListPresenter
@Inject
constructor(model: FavoriteImageListContract.Model, rootView: FavoriteImageListContract.View) :
        FavoritePresenter<FavoriteImageListContract.Model, FavoriteImageListContract.View>(model, rootView)


