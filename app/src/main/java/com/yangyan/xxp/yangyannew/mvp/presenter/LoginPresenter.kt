package com.yangyan.xxp.yangyannew.mvp.presenter

import android.app.Application
import com.google.gson.Gson

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.RxLifecycleUtils
import com.yangyan.xxp.yangyannew.app.Preference
import com.yangyan.xxp.yangyannew.mvp.contract.LoginContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.LoginInfo

import me.jessyan.rxerrorhandler.core.RxErrorHandler

import javax.inject.Inject

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
@ActivityScope
class LoginPresenter @Inject
constructor(model: LoginContract.Model, rootView: LoginContract.View)
    : BasePresenter<LoginContract.Model, LoginContract.View>(model, rootView) {
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

    private var mUserInfoString by Preference("userInfo", "")
    private var mLoginInfoString by Preference("loginInfo", "")

    fun cleanUserInfo() {
        mUserInfoString = ""
    }

    fun toLogin(email: String, pwd: String) {
        if (email.isEmpty() || pwd.isEmpty()) {
            mRootView.showMessage("任何一项不能为空")
            return
        }
        //保存用户登录信息
        val loginInfo = LoginInfo(email,pwd)
        mLoginInfoString = mGson.toJson(loginInfo)


        val userInfo = UserInfo()
        userInfo.username = email
        userInfo.setPassword(pwd)

        mModel.toLogin(userInfo)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    mRootView.showLoading()
                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { mRootView.hideLoading() }
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<UserInfo>(mErrorHandler) {
                    override fun onNext(t: UserInfo) {
                        //存储用户信息
                        mUserInfoString = mGson.toJson(t)
                        mRootView.loginSuccess(t)
                        Timber.i("用户信息 : ${t.toString()}")
                    }
                })

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
