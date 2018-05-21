package com.yangyan.xxp.yangyannew.mvp.presenter

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.mvp.BaseModel
import com.yangyan.xxp.yangyannew.mvp.contract.HomeContract

import me.jessyan.rxerrorhandler.core.RxErrorHandler

import javax.inject.Inject

import com.yangyan.xxp.yangyannew.mvp.contract.MainContract
import com.yangyan.xxp.yangyannew.mvp.model.HomeModel
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description :主页面
 */
@ActivityScope
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
    lateinit var mHomeModel: HomeModel

    fun getHomeData() {
        mHomeModel.getHomeData(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(

                )
    }

    fun loadMoreHomeData() {

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
