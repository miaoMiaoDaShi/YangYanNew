package com.yangyan.xxp.yangyannew.mvp.presenter

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils
import com.tencent.cos.xml.CosXmlSimpleService
import com.yangyan.xxp.yangyannew.mvp.contract.FavoriteContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.FavoriteInfo
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.HomeAdapter
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.MineFavoriteAdapter
import es.dmoral.toasty.Toasty
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject
import javax.inject.Named

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/29
 * Description :  收藏相关   强求继承
 */
abstract class FavoritePresenter<Model : FavoriteContract.Model, View : FavoriteContract.View>
(model: Model, view: View)
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

    @Inject
    @field:Named("FavoriteImagesAdapter")
    lateinit var mHomeAdapter: HomeAdapter
    @Inject
    @field:Named("FavoriteImagesDatas")
    lateinit var mImageDatas: MutableList<ImagesInfo>


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
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<List<FavoriteInfo>>(mErrorHandler) {
                    override fun onNext(t: List<FavoriteInfo>) {
                        mRootView.favoriteDataStatus(t.isEmpty())
                        mDatas.clear()
                        mDatas.addAll(t)
                        mAdapter.notifyDataSetChanged()
                    }
                })
    }

    /**
     * 添加到收藏夹
     */
    fun addImageCollectToFavorite(favorites: List<FavoriteInfo>, imageCollect: ImagesInfo) {
        mModel.addImageCollectToFavorite(favorites, imageCollect)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    mRootView.showLoading()
                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { mRootView.hideLoading() }
                .subscribe(object : ErrorHandleSubscriber<String>(mErrorHandler) {
                    override fun onNext(t: String) {
                        mRootView.showAddImageToFavoriteSuccess()
                        Toasty.success(mApplication, "收藏成功").show()
                        mRootView.killMyself()
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        mRootView.showAddImageToFavoriteFailed()
                    }
                })
    }

    /**
     * 根据收藏夹的id  获得收藏夹里的套图
     */
    fun getImageCollectByFavorite(favorite:FavoriteInfo) {
        mModel.getImageCollectByFavorite(favorite)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    mRootView.showLoading()
                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { mRootView.hideLoading() }
                .subscribe(object : ErrorHandleSubscriber<List<ImagesInfo>>(mErrorHandler) {
                    override fun onNext(t: List<ImagesInfo>) {
                        mRootView.favoriteDataStatus(t.isEmpty())
                        mImageDatas.clear()
                        mImageDatas.addAll(t)
                        mHomeAdapter.notifyDataSetChanged()
                    }
                })
    }

}