package com.yangyan.xxp.yangyannew.mvp.presenter

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils
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

    /**
     * 上传封面文件
     */
    fun addFavoriteCover(imagePath: String, context: Context) {
        mModel.uploadCover(imagePath, context)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    mRootView.showLoading()
                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { mRootView.hideLoading() }
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<String>(mErrorHandler) {
                    override fun onNext(t: String) {
                        mRootView.onUploadCoverSuccess(t)
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        mRootView.onUploadCoverFailed()
                    }

                })
    }

    fun addFavorite(coverUrl: String, title: String) {
        if (coverUrl.isEmpty() || title.isEmpty()) {
            Toasty.error(mApplication, "任何一项不能为空").show()
            return
        }
        val favoriteInfo = FavoriteInfo()
        favoriteInfo.coverUrl = coverUrl
        favoriteInfo.title = title
        mModel.addFavorite(favoriteInfo)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    mRootView.showLoading()
                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { mRootView.hideLoading() }
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<String>(mErrorHandler) {
                    override fun onNext(t: String) {
                        Toasty.success(mApplication, "新建收藏夹成功").show()
                        mRootView.killMyself()
                    }


                })
    }




}