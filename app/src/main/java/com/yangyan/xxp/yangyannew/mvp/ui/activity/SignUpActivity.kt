package com.yangyan.xxp.yangyannew.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
        setLoading(true)
    }

    /**
     * 设置控件不可编辑
     */
    private fun setLoading(b: Boolean) {
        val temp = !b
        mFabToSignUp.visibility = if (b) View.INVISIBLE else View.VISIBLE
        mProBar.visible(b)
        mEtEmail.isEnabled = temp
        mEtPwd.isEnabled = temp
        mEtConfirmPwd.isEnabled = temp
        mTvToLogin.isClickable = temp
    }

    override fun launchActivity(intent: Intent) {
    }

    override fun hideLoading() {
        setLoading(false)
    }

    override fun killMyself() {
        finish()
    }

    override fun showMessage(message: String) {
        Toasty.error(applicationContext, message).show()
    }

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerSignUpComponent.builder()
                .appComponent(appComponent)
                .signUpModule(SignUpModule(this))
                .build()
                .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_sign_up
    }

    override fun signUpSuccess(userInfo: UserInfo) {
        Toasty.success(applicationContext, "注册成功,欢迎您的加入", Toast.LENGTH_LONG).show()
    }

    override fun initData(savedInstanceState: Bundle?) {
        bindListener()
    }

    private fun bindListener() {
        mFabToSignUp.onClick {
            val email = mEtEmail.text.toString().trim()
            val pwd = mEtPwd.text.toString()
            val confirmPwd = mEtConfirmPwd.text.toString()


            mPresenter?.toSignUp(email, pwd, confirmPwd)
        }
        mTvToLogin.onClick {
            finish()
        }
    }
}