package com.yangyan.xxp.yangyannew.mvp.presenter

import android.app.Application
import com.google.gson.Gson

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.yangyan.xxp.yangyannew.app.Preference

import me.jessyan.rxerrorhandler.core.RxErrorHandler

import javax.inject.Inject

import com.yangyan.xxp.yangyannew.mvp.contract.MineContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.FavoriteInfo
import com.yangyan.xxp.yangyannew.mvp.model.entity.UserInfo
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.MineFavoriteAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import timber.log.Timber

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description : 个人
 */
@FragmentScope
class MinePresenter @Inject
constructor(model: MineContract.Model, rootView: MineContract.View)
    : FavoritePresenter<MineContract.Model, MineContract.View>(model, rootView) {

    private var mUserInfoString by Preference("userInfo", "")


    fun getUserInfo() {
        val userInfo = mGson.fromJson(mUserInfoString, UserInfo::class.java)
        mModel.loadMineData(userInfo)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    mRootView.showLoading()
                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { mRootView.hideLoading() }
                .subscribe(object : ErrorHandleSubscriber<UserInfo>(mErrorHandler) {
                    override fun onNext(t: UserInfo) {
                        mRootView.loadUserInfoSuccess(t)
                        Timber.i("用户信息 : ${t.toString()}")
                    }
                })
    }



    override fun onDestroy() {
        super.onDestroy()
    }
}
