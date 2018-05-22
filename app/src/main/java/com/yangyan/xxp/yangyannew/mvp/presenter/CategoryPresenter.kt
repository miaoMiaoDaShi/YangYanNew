package com.yangyan.xxp.yangyannew.mvp.presenter

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.yangyan.xxp.yangyannew.mvp.contract.CategoryContract

import me.jessyan.rxerrorhandler.core.RxErrorHandler

import javax.inject.Inject

import com.yangyan.xxp.yangyannew.mvp.contract.MainContract


@FragmentScope
class CategoryPresenter
@Inject
constructor(model: CategoryContract.Model, rootView: CategoryContract.View)
    : BasePresenter<CategoryContract.Model, CategoryContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    override fun onDestroy() {
        super.onDestroy()
    }
}
