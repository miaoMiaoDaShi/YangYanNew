package com.yangyan.xxp.yangyannew.mvp.presenter

import android.app.Application
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils
import com.yangyan.xxp.yangyannew.mvp.contract.LoginContract
import com.yangyan.xxp.yangyannew.mvp.contract.SignUpContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.UserInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import timber.log.Timber
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/23
 * Description :注册
 */
@ActivityScope
class SignUpPresenter @Inject
constructor(model: SignUpContract.Model, rootView: SignUpContract.View)
    : BasePresenter<SignUpContract.Model, SignUpContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    fun toSignUp(email: String, pwd: String, confirmPwd: String) {
        if (email.isEmpty() || pwd.isEmpty() || confirmPwd.isEmpty()) {
            mRootView.showMessage("任何一项不能为空")
            return
        }

        if (pwd != confirmPwd) {
            mRootView.showMessage("两次输入的密码不一致")
            return
        }
        val userInfo = UserInfo()
        userInfo.email = email
        userInfo.username = email
        userInfo.setPassword(pwd)

        mModel.toSignUp(userInfo)
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
                        mRootView.signUpSuccess(t)
                        mRootView.killMyself()
                        Timber.i("用户信息 : ${t.toString()}")
                    }
                })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
