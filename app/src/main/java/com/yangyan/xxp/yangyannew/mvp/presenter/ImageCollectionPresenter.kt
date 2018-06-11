package com.yangyan.xxp.yangyannew.mvp.presenter

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.utils.RxLifecycleUtils
import com.yangyan.xxp.yangyannew.mvp.contract.ImageCollectionContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.ImageCollectionAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject
import javax.inject.Named

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/25
 * Description : 套图详情
 */
@ActivityScope
class ImageCollectionPresenter @Inject
constructor(model: ImageCollectionContract.Model, rootView: ImageCollectionContract.View) :
        FavoritePresenter<ImageCollectionContract.Model, ImageCollectionContract.View>(model, rootView) {

    @Inject
    @field:Named("ImageCollectionImagesAdapter")
    lateinit var mImageCollectionAdapter: ImageCollectionAdapter
    @Inject
    @field:Named("ImageCollectionImagesDatas")
    lateinit var mData: MutableList<ImagesInfo>


    fun getIamgeCollection(id: String) {
        mModel.getImageCollection(id)
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
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<List<ImagesInfo>>(mErrorHandler) {
                    override fun onNext(t: List<ImagesInfo>) {
                        t.forEach(::println)
                        mData.addAll(t)
                        mImageCollectionAdapter.notifyDataSetChanged()
                    }
                })
    }

}