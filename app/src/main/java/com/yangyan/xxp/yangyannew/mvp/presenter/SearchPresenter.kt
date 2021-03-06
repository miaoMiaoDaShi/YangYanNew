package com.yangyan.xxp.yangyannew.mvp.presenter

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.RxLifecycleUtils

import me.jessyan.rxerrorhandler.core.RxErrorHandler

import javax.inject.Inject

import com.yangyan.xxp.yangyannew.mvp.contract.SearchContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.SearchAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import retrofit2.HttpException
import timber.log.Timber

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description : 搜索
 */
@FragmentScope
class SearchPresenter @Inject
constructor(model: SearchContract.Model, rootView: SearchContract.View) : BasePresenter<SearchContract.Model, SearchContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager
    @Inject
    lateinit var mAdapter: SearchAdapter
    @Inject
    lateinit var mData: MutableList<ImagesInfo>
    /**
     * 页数是从 1开始的
     */
    private var mPageIndex = 1

    private var mSearchDisposable: Disposable? = null
    fun searchAtlasByKeyword(pullToRefresh: Boolean, keyWords: String) {
        if (pullToRefresh) mPageIndex = 1
        mAdapter.setKeyWords(keyWords)
        Observable.just(keyWords)
                .filter {
                    if (it.isEmpty()) {
                        mData.clear()
                        mAdapter.notifyDataSetChanged()
                        mRootView.searchResultStatus(false)
                    }
                    it.isNotEmpty()
                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .concatMap {
                    mModel.searchImagesByKeyword(mPageIndex, it).apply {
                        mAdapter.setKeyWords(it)
                    }
                }
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    mSearchDisposable?.dispose()
                    mSearchDisposable = it
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
                        mRootView.searchResultStatus(mData.isEmpty())
                    }

                    override fun onError(t: Throwable) {

                    }
                })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
