package com.yangyan.xxp.yangyannew.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.app.onClick
import com.yangyan.xxp.yangyannew.app.visible
import com.yangyan.xxp.yangyannew.di.component.DaggerSignUpComponent
import com.yangyan.xxp.yangyannew.di.module.SignUpModule
import com.yangyan.xxp.yangyannew.mvp.contract.SignUpContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.UserInfo
import com.yangyan.xxp.yangyannew.mvp.presenter.SignUpPresenter
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_sign_up.*

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/23
 * Description :
 */
class SignUpActivity : BaseActivity<SignUpPresenter>(), SignUpContract.View {
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
        return R.layout.activity_sign_up
    }

    override fun initData(savedInstanceState: Bundle?) {
        bindListener()
    }

    private fun bindListener() {
        mFabToSignUp.onClick {
            val email = mEtEmail.text.toString().trim()
            val pwd = mEtPwd.text.toString()
            val confirmPwd = mEtConfirmPwd.text.toString()

            if (email.isEmpty() || pwd.isEmpty() || confirmPwd.isEmpty()) {
                Toasty.error(applicationContext, "任何一项不能为空").show()
                return@onClick
            }

            if (pwd != confirmPwd) {
                Toasty.error(applicationContext, "两次输入的密码不一致").show()
                return@onClick
            }
            mFabToSignUp.visible(false)
            mProBar.visible(true)
            val userInfo = UserInfo("http://bmob-cdn-19399.b0.upaiyun.com/2018/05/23/4cb77ae840c8f9108028c2edf75cc350.png")
            userInfo.email = email
            userInfo.setPassword(pwd)
            mPresenter?.toSignUp(userInfo)
        }
        mTvToLogin.onClick {
            finish()
        }
    }
}