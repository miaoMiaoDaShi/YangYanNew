package com.yangyan.xxp.yangyannew.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.app.onClick
import com.yangyan.xxp.yangyannew.app.visible
import com.yangyan.xxp.yangyannew.di.component.DaggerLoginComponent
import com.yangyan.xxp.yangyannew.di.module.LoginModule
import com.yangyan.xxp.yangyannew.mvp.contract.LoginContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.UserInfo
import com.yangyan.xxp.yangyannew.mvp.presenter.LoginPresenter
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/23
 * Description :登录页面
 */
class LoginActivity : BaseActivity<LoginPresenter>(), LoginContract.View {
    override fun loginSuccess(userInfo: UserInfo) {
        startActivity<MainActivity>()
        finish()

    }

    override fun showLoading() {
        setLoading(true)
    }

    private fun setLoading(b: Boolean) {
        mProBar.visible(b)
        mFabToLogin.visibility = if (b) View.INVISIBLE else View.VISIBLE
        mEtEmail.isEnabled = !b
        mEtPwd.isEnabled = !b
        mTvToSignUp.isClickable = !b


    }

    override fun launchActivity(intent: Intent) {
    }

    override fun hideLoading() {
        setLoading(false)
    }

    override fun killMyself() {
    }

    override fun showMessage(message: String) {
        Toasty.error(applicationContext, message).show()
    }

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerLoginComponent.builder()
                .appComponent(appComponent)
                .loginModule(LoginModule(this))
                .build()
                .inject(this)

    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_login
    }

    override fun initData(savedInstanceState: Bundle?) {
        mPresenter?.cleanUserInfo()
        bindListener()
    }

    private fun bindListener() {
        mFabToLogin.onClick {
            val email = mEtEmail.text.toString()
            val pwd = mEtPwd.text.toString()
            mPresenter?.toLogin(email, pwd)
        }
        mTvToSignUp.onClick {
            startActivity<SignUpActivity>()
        }

    }
}


