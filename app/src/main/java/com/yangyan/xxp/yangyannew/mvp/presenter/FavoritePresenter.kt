package com.yangyan.xxp.yangyannew.mvp.presenter

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.yangyan.xxp.yangyannew.mvp.contract.AddFavoriteContract
import com.yangyan.xxp.yangyannew.mvp.contract.FavoriteContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.FavoriteInfo
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.MineFavoriteAdapter
import es.dmoral.toasty.Toasty
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/29
 * Description :  收藏相关   强求继承
 */
abstract class FavoritePresenter<Model:FavoriteContract.Model,View:FavoriteContract.View>
(model:Model,view:View)
    : BasePresenter<Model, View>(model, view) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager
    @Inject
    lateinit var mGson: Gson
    @Inject
    lateinit var mAdapter: MineFavoriteAdapter
    @Inject
    lateinit var mDatas: MutableList<FavoriteInfo>

    /**
     * 上传封面文件
     */
    fun addFavoriteCover(imagePath: String) {
        mModel.uploadCover(imagePath)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    mRootView.showLoading()
                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { mRootView.hideLoading() }
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
                .subscribe(object : ErrorHandleSubscriber<String>(mErrorHandler) {
                    override fun onNext(t: String) {
                        Toasty.success(mApplication, "新建收藏夹成功").show()
                        mRootView.killMyself()
                    }


                })
    }

    /**
     * 获取收藏信息
     */
    fun getFavoriteList() {
        mModel.getFavorite()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    mRootView.showLoading()
                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { mRootView.hideLoading() }
                .subscribe(object : ErrorHandleSubscriber<List<FavoriteInfo>>(mErrorHandler) {
                    override fun onNext(t: List<FavoriteInfo>) {
                        mRootView.favoriteDataStatus(t.isEmpty())
                        mDatas.clear()
                        mDatas.addAll(t)
                        mAdapter.notifyDataSetChanged()
                    }
                })
    }
}