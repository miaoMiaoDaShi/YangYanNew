package com.yangyan.xxp.yangyannew.mvp.presenter

import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.utils.RxLifecycleUtils
import com.yangyan.xxp.yangyannew.app.Preference

import javax.inject.Inject

import com.yangyan.xxp.yangyannew.mvp.contract.MineContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.MineZipInfo
import com.yangyan.xxp.yangyannew.mvp.model.entity.UserInfo
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
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<MineZipInfo>(mErrorHandler) {
                    override fun onNext(t: MineZipInfo) {
                        mRootView.loadUserInfoSuccess(t.userInfo)
                        Timber.i("用户信息 : ${t.toString()}")
                        replaceDataForFavorites(t.favorites)
                    }
                })
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}
