package com.yangyan.xxp.yangyannew.mvp.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.http.imageloader.glide.ImageConfigImpl
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.app.Constant
import com.yangyan.xxp.yangyannew.app.Preference
import com.yangyan.xxp.yangyannew.di.component.DaggerSplashComponent
import com.yangyan.xxp.yangyannew.di.module.SplashModule
import com.yangyan.xxp.yangyannew.mvp.contract.SplashContract
import com.yangyan.xxp.yangyannew.mvp.presenter.SplashPresenter
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/23
 * Description : 启动页面
 */
class SplashActivity : BaseActivity<SplashPresenter>(), SplashContract.View {
    @Inject
    lateinit var mImageLoader: ImageLoader



    override fun showLoading() {


    }

    override fun launchActivity(intent: Intent) {
    }

    override fun getActivity(): Activity {
        return this
    }

    override fun hideLoading() {
    }

    override fun killMyself() {
        finish()
    }

    override fun showMessage(message: String) {
        Toasty.error(applicationContext, message).show()
    }

    override fun countDown(count: Long) {

    }

    override fun alreadyHavePermission() {
        //加载本地图片
        mImageLoader.loadImage(applicationContext,
                ImageConfigImpl.builder()
                        .url(Constant.SPLASH_LOCAL_PATH + Constant.SPLASH_LOCAL_NAME)
                        .imageView(mIvSplash)
                        .errorPic(R.drawable.bg_default_splash)
                        .build()
        )


    }

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerSplashComponent.builder()
                .appComponent(appComponent)
                .splashModule(SplashModule(this))
                .build()
                .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_splash
    }

    override fun initData(savedInstanceState: Bundle?) {
        mPresenter?.getSplashImageInfo()
    }

}