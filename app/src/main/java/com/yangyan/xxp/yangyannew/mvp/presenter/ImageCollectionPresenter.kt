package com.yangyan.xxp.yangyannew.mvp.presenter

import cn.bmob.v3.BmobUser
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.utils.RxLifecycleUtils
import com.yangyan.xxp.yangyannew.mvp.contract.ImageCollectionContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.UserInfo
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
    lateinit var mData: MutableList<String>


    fun getImageCollection(id: Int) {
        mModel.getImageCollectByIdAndUser(id, BmobUser.getCurrentUser(UserInfo::class.java))
                .flatMap {
                    mRootView.thisImageCollectIsFavorited(it.isNotEmpty())
                    mModel.getImageCollection(id)
                }
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    mRootView.showLoading()
                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    mRootView.hideLoading()
                }
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<List<String>>(mErrorHandler) {
                    override fun onNext(t: List<String>) {
                        if (t.isNotEmpty()) {
                            mRootView.setCoverImage(t[0])
                            mImageCollectionAdapter.replaceData(t)
                        }

                    }
                })
    }

}