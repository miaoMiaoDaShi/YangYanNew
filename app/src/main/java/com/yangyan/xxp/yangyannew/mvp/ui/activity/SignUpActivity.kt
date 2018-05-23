package com.yangyan.xxp.yangyannew.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.di.component.DaggerSignUpComponent
import com.yangyan.xxp.yangyannew.di.module.SignUpModule
import com.yangyan.xxp.yangyannew.mvp.contract.SignUpContract
import com.yangyan.xxp.yangyannew.mvp.presenter.SignUpPresenter

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/23
 * Description :
 */
class SignUpActivity:BaseActivity<SignUpPresenter>(),SignUpContract.View {
    override fun showLoading() {


    }

    override fun launchActivity(intent: Intent) {
    }

    override fun hideLoading() {
    }

    override fun killMyself() {
    }

    override fun showMessage(message: String) {
    }

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerSignUpComponent.builder()
                .appComponent(appComponent)
                .signUpModule(SignUpModule(this))
                .build()
                .inject(this)
                //signUpModule(SignUpModule(this))
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return  R.layout.activity_sign_up
    }

    override fun initData(savedInstanceState: Bundle?) {
    }
}