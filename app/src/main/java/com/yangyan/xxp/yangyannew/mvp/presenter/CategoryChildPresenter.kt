package com.yangyan.xxp.yangyannew.mvp.presenter

import android.app.Application
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils
import com.yangyan.xxp.yangyannew.mvp.contract.CategoryChildContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.HomeAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/22
 * Description :
 */
@FragmentScope
class CategoryChildPresenter @Inject
constructor(model: CategoryChildContract.Model, rootView: CategoryChildContract.View)
    : BasePresenter<CategoryChildContract.Model, CategoryChildContract.View>(model, rootView) {
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

    fun getCategoryData(categoryCode: String, pullToRefresh: Boolean) {
        Timber.i(categoryCode)
        if (pullToRefresh) mPageIndex = 1


        mModel.getCategoryChildData(categoryCode, mPageIndex)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    if (pullToRefresh)
                        mRootView.showLoading()//显示下拉刷新的进度条
                    else {
                        mRootView.startLoadMore()//显示上拉加载更多的进度条

                    }

                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    if (pullToRefresh)
                        mRootView.hideLoading()//隐藏下拉刷新的进度条
                    else{
                        mAdapter.loadMoreComplete()
                        mRootView.endLoadMore()//隐藏上拉加载更多的进度条
                    }
                }
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(object : ErrorHandleSubscriber<List<ImagesInfo>>(mErrorHandler) {
                    override fun onNext(t: List<ImagesInfo>) {
                        mPageIndex++
                        if (pullToRefresh) {
                            mData.clear()
                            mData.addAll(t)
                            mAdapter.notifyDataSetChanged()
                        } else {
                            mData.addAll(t)
                            mAdapter.notifyItemRangeChanged(mData.size - t.size, mData.size)
                        }

                    }
//
//                    override fun onError(t: Throwable) {
//                        if (t is HttpException) {
//                            //404代表 没有很多的额页数了
//                            if (t.code() == 404) {
//                                mAdapter.loadMoreEnd()
//                            }
//                        }
//                    }
                })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}