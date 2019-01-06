package com.yangyan.xxp.yangyannew.mvp.presenter

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils
import com.yangyan.xxp.yangyannew.mvp.contract.FavoriteContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.FavoriteInfo
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.HomeAdapter
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.MineFavoriteAdapter
import es.dmoral.toasty.Toasty
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import timber.log.Timber
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
                        mRootView.onLoadFavorites(t)
                        replaceDataForFavorites(t)
                    }
                })
    }

    fun getFavoriteListWithStatus(id: Int) {
        mModel.getFavorite()
                .zipWith(mModel.getFavoritesByImageCollectId(id), BiFunction<List<FavoriteInfo>, List<FavoriteInfo>, List<FavoriteInfo>> { t1, t2 ->
                    val newFavoriteInfos = mutableListOf<FavoriteInfo>()
                    newFavoriteInfos.addAll(t1)
                    newFavoriteInfos.forEach outer@{
                        t2.forEach inner@{ favoriteInfo ->
                            Timber.i("收藏的图集${favoriteInfo}")
                            if (favoriteInfo.objectId == it.objectId) {
                                it.isChecked = true
                                return@outer
                            } else {
                                it.isChecked = false
                            }
                        }
                    }
                    newFavoriteInfos.forEach {
                        Timber.i("收藏的图集------${it}")
                    }
                    newFavoriteInfos
                })
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
                        mRootView.onLoadFavorites(t)
                        replaceDataForFavorites(t)
                    }
                })
    }

    /**
     * 更新收藏列表 RecyclerView
     */
    protected fun replaceDataForFavorites(t: List<FavoriteInfo>) {
        mDatas.clear()
        mDatas.addAll(t)
        mAdapter.notifyDataSetChanged()
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
     * 从选择的收藏夹中除该图集
     */
    fun delImageCollectToFavorite(favorites: List<FavoriteInfo>, imageCollect: ImagesInfo) {
        mModel.delImageCollectByFavorite(favorites, imageCollect)
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
                        Toasty.success(mApplication, "你的图图离你而去了.").show()
                        mRootView.killMyself()
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        mRootView.showAddImageToFavoriteFailed()
                    }
                })
    }

    /**
     * 添加删除
     */
    fun addWithDelImageCollectToFavorite(favoritesForAdd: List<FavoriteInfo>, favoritesForDel: List<FavoriteInfo>, imageCollect: ImagesInfo) {
        (if (favoritesForAdd.isEmpty()) {
            Observable.just("200")
        } else {
            mModel.addImageCollectToFavorite(favoritesForAdd, imageCollect)
        })
                .flatMap {
                    if (favoritesForDel.isEmpty()) {
                        Observable.just("200")
                    } else {
                        mModel.delImageCollectByFavorite(favoritesForDel, imageCollect)
                    }
                }
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
                        Toasty.success(mApplication, "嗯!好像成功了~").show()
                        mRootView.killMyself()
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        mRootView.showAddImageToFavoriteFailed()
                    }
                })
    }

    /**
     * 删除收藏夹
     */
    fun delFavorite(favoriteInfo: FavoriteInfo) {
        mModel.delFavorite(favoriteInfo, mImageDatas)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    mRootView.showLoading()
                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { mRootView.hideLoading() }
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<Int>(mErrorHandler) {
                    override fun onNext(t: Int) {
                        if (mImageDatas.size == t) {
                            Toasty.success(mApplication, "删除成功").show()
                            mRootView.killMyself()
                        }
                    }
                })
    }

    /**
     * 根据收藏夹的id  获得收藏夹里的套图
     */
    fun getImageCollectByFavorite(favorite: FavoriteInfo) {
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
                        mRootView.favoriteImagesStatus(t.isEmpty())
                        mImageDatas.clear()
                        mImageDatas.addAll(t)
                        mHomeAdapter.notifyDataSetChanged()
                    }
                })
    }

    fun queryFavoritesByImageCollectId(id: Int) {
        mModel.getFavoritesByImageCollectId(id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    mRootView.showLoading()
                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { mRootView.hideLoading() }
                .subscribe(object : ErrorHandleSubscriber<List<FavoriteInfo>>(mErrorHandler) {
                    override fun onNext(t: List<FavoriteInfo>) {
                        Timber.i("包含该图集的收藏夹${t.size}")
                        mDatas.forEach outer@{
                            t.forEach inner@{ favoriteInfo ->
                                if (favoriteInfo.objectId == it.objectId) {
                                    it.isChecked = true
                                    return@inner
                                } else {
                                    it.isChecked = false
                                }
                            }
                        }
                        mAdapter.notifyDataSetChanged()

                    }
                })
    }

}