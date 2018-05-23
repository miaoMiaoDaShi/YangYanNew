package com.yangyan.xxp.yangyannew.mvp.ui.activity

import android.os.Bundle
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.mvp.presenter.LoginPresenter

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/23
 * Description :
 */
class LoginActivity:BaseActivity<LoginPresenter>() {
    override fun setupActivityComponent(appComponent: AppComponent) {


    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return  R.layout.activity_sign_up
    }

    override fun initData(savedInstanceState: Bundle?) {
    }
}