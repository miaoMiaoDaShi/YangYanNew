package com.yangyan.xxp.yangyannew.mvp.presenter

import android.app.Application
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.yangyan.xxp.yangyannew.mvp.contract.HomeContract
import com.yangyan.xxp.yangyannew.mvp.contract.ImageCollectionContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.HomeAdapter
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.ImageCollectionAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import timber.log.Timber
import java.util.*
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/25
 * Description : 套图详情
 */
@ActivityScope
class ImageCollectionPresenter @Inject
constructor(model: ImageCollectionContract.Model, rootView: ImageCollectionContract.View) :
        BasePresenter<ImageCollectionContract.Model, ImageCollectionContract.View>(model, rootView) {

    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager
    @Inject
    lateinit var mAdapter: ImageCollectionAdapter
    @Inject
    lateinit var mData: MutableList<ImagesInfo>


    fun getIamgeCollection(id: String) {
        mModel.getIamgeCollection(id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    mRootView.showLoading()
                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .map { t ->
                    val typeA = mutableListOf<ImagesInfo>()
                    val typeB = mutableListOf<ImagesInfo>()
                    val typeMixture = mutableListOf<ImagesInfo>()
                    t.forEach {
                        if (it.width > it.height) {
                            typeA.add(it)
                        } else {
                            typeB.add(it)
                        }
                    }
                    typeMixture.addAll(typeA)
                    typeMixture.addAll(typeB)
                    typeMixture
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    mRootView.hideLoading()
                }
                .subscribe(object : ErrorHandleSubscriber<List<ImagesInfo>>(mErrorHandler) {
                    override fun onNext(t: List<ImagesInfo>) {
                        t.forEach(::println)
                        mData.addAll(t)
                        mAdapter.notifyDataSetChanged()
                    }
                })
    }

}