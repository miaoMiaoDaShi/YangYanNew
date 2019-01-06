package com.yangyan.xxp.yangyannew.mvp.presenter

import android.app.Application
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.RxLifecycleUtils
import com.yangyan.xxp.yangyannew.mvp.contract.HomeContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo

import me.jessyan.rxerrorhandler.core.RxErrorHandler

import javax.inject.Inject

import com.yangyan.xxp.yangyannew.mvp.ui.adapter.HomeAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import retrofit2.HttpException

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description :主页面
 */
@FragmentScope
class HomePresenter @Inject
constructor(model: HomeContract.Model, rootView: HomeContract.View) :
        BasePresenter<HomeContract.Model, HomeContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager
    @Inject
    lateinit var mAdapter: HomeAdapter
    @Inject
    lateinit var mData: MutableList<ImagesInfo>

    /**
     * 页数是从 1开始的
     */
    private var mPageIndex = 1

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    internal fun onCreate() {

    }

    fun getHomeData(pullToRefresh: Boolean) {
        if (pullToRefresh) mPageIndex = 1


        mModel.getHomeData(mPageIndex)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    if (pullToRefresh)
                        mRootView.showLoading()//显示下拉刷新的进度条
                    else
                        mRootView.startLoadMore()//显示上拉加载更多的进度条
                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    if (pullToRefresh)
                        mRootView.hideLoading()//隐藏下拉刷新的进度条
                    else {
                        mRootView.endLoadMore()//隐藏上拉加载更多的进度条

                    }
                }
                .doOnError {
                    mAdapter.loadMoreEnd()
                }
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(object : ErrorHandleSubscriber<List<ImagesInfo>>(mErrorHandler) {
                    override fun onNext(t: List<ImagesInfo>) {
                        mPageIndex++
                        if (pullToRefresh) {
                         mAdapter.replaceData(t)
                        } else {
                           mAdapter.addData(t)
                            mAdapter.loadMoreComplete()
                        }

                    }

                    override fun onError(t: Throwable) {

                    }

                })
    }

    /**
     * 获取系统信息
     */
    fun getSystemMsg() {
        mModel.getSystemMsg()
                .subscribeOn(Schedulers.io())
//                .doOnSubscribe {
//                    mRootView.showLoading()
//                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                //.doFinally { mRootView.hideLoading() }
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<String>(mErrorHandler) {
                    override fun onNext(t: String) {
                        mRootView.loadSystemMagSuccess(t)
                    }
                })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
