package com.yangyan.xxp.yangyannew.mvp.presenter

import android.app.Application
import android.view.View

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.yangyan.xxp.yangyannew.mvp.contract.GalleryContract

import me.jessyan.rxerrorhandler.core.RxErrorHandler

import javax.inject.Inject

import com.yangyan.xxp.yangyannew.mvp.model.ImageCollectionModel
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.GalleryPageAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Provider

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description :
 */
@ActivityScope
class GalleryPresenter @Inject
constructor(model: GalleryContract.Model, rootView: GalleryContract.View)
    : BasePresenter<GalleryContract.Model, GalleryContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager
    @Inject
    lateinit var mImageCollectionModel: ImageCollectionModel
    @Inject
    lateinit var mAdapter: GalleryPageAdapter
    @Inject
    lateinit var mDatas: MutableList<ImagesInfo>
//    @Inject
//    lateinit var mItemViewProvider: Provider<ImagesInfo>

    fun getIamgeCollection(id: String) {
        mImageCollectionModel.getIamgeCollection(id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    //mRootView.showLoading()
                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    //mRootView.hideLoading()
                }
                .subscribe(object : ErrorHandleSubscriber<List<ImagesInfo>>(mErrorHandler) {
                    override fun onNext(t: List<ImagesInfo>) {
                        mDatas.addAll(t)
                        mAdapter.notifyDataSetChanged()
                        mRootView.loadImageCollectionSuccess(t)
                    }
                })
    }

    override fun onDestroy() {

        super.onDestroy()
    }
}
