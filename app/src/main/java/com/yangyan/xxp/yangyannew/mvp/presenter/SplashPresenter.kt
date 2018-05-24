package com.yangyan.xxp.yangyannew.mvp.presenter

import android.Manifest
import android.app.Application
import android.content.Intent
import android.icu.util.Calendar
import com.google.gson.Gson
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.PermissionUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yangyan.xxp.yangyannew.app.Constant
import com.yangyan.xxp.yangyannew.app.Preference
import com.yangyan.xxp.yangyannew.mvp.contract.LoginContract
import com.yangyan.xxp.yangyannew.mvp.contract.SplashContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.SplashImageInfo
import com.yangyan.xxp.yangyannew.mvp.ui.activity.LoginActivity
import com.yangyan.xxp.yangyannew.mvp.ui.activity.MainActivity
import com.yangyan.xxp.yangyannew.mvp.ui.service.DownloadService
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/23
 * Description :
 */
@ActivityScope
class SplashPresenter @Inject
constructor(model: SplashContract.Model, rootView: SplashContract.View)
    : BasePresenter<SplashContract.Model, SplashContract.View>(model, rootView) {
    /**
     * 本地的splashImage相关信息
     */
    private var mSplashImageInfo by Preference("splashImage", "")
    private val mUserInfoString by Preference("userInfo", "")
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager
    @Inject
    lateinit var mRxPermissions: RxPermissions
    @Inject
    lateinit var mGson: Gson

    fun getSplashImageInfo() {

        PermissionUtil.requestPermission(object : PermissionUtil.RequestPermission {
            override fun onRequestPermissionFailureWithAskNeverAgain(permissions: MutableList<String>?) {

            }

            override fun onRequestPermissionSuccess() {
                mRootView.alreadyHavePermission()
                startCountDown()
                toLoadSplahInfo()
            }

            override fun onRequestPermissionFailure(permissions: MutableList<String>?) {
                mRootView.showMessage("拒绝相关权限,软件无法使用.")
                mRootView.killMyself()
                return
            }
        }, mRxPermissions, mErrorHandler, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)


    }

    /**
     * 加载信息
     */
    private fun toLoadSplahInfo() {
        val splashImageInfo = mGson.fromJson(mSplashImageInfo, SplashImageInfo::class.java)
        //系统当前时间
        val time = SimpleDateFormat("YYYY-MM-dd").format(Date(System.currentTimeMillis()))
        //是否需要更新
        var shouldUpdata = true
        splashImageInfo?.let {
            shouldUpdata = it.results[0].publishedAt.split("T")[0] != time
        }
        if (shouldUpdata) {
            mModel.loadSplashImage()
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe {
                        mRootView.showLoading()
                    }
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally { mRootView.hideLoading() }
                    .subscribe(object : ErrorHandleSubscriber<SplashImageInfo>(mErrorHandler) {
                        override fun onNext(t: SplashImageInfo) {
                            mSplashImageInfo = mGson.toJson(t)
                            //下载图片
                            val intent = Intent(mApplication, DownloadService::class.java)
                            intent.putExtra("downloadUrl", t.results[0].url)
                            intent.putExtra("name", Constant.SPLASH_LOCAL_NAME)
                            mApplication.startService(intent)

                        }
                    })
        }
    }

    /**
     * 计时
     */
    private fun startCountDown() {
        Observable.intervalRange(1, 4, 0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ErrorHandleSubscriber<Long>(mErrorHandler) {
                    override fun onNext(t: Long) {
                        val currentCount = 4 - t
                        if (currentCount == 0L) {
                            if (mUserInfoString.isEmpty()) {
                                mRootView.getActivity().startActivity<LoginActivity>()
                            } else {
                                mRootView.getActivity().startActivity<MainActivity>()
                            }
                            mRootView.killMyself()

                        }
                        mRootView.countDown(currentCount)
                    }
                })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}