package com.yangyan.xxp.yangyannew.mvp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.DeviceUtils.getVersionName

import com.yangyan.xxp.yangyannew.di.component.DaggerAboutComponent
import com.yangyan.xxp.yangyannew.di.module.AboutModule
import com.yangyan.xxp.yangyannew.mvp.contract.AboutContract
import com.yangyan.xxp.yangyannew.mvp.presenter.AboutPresenter

import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.app.onClick
import com.yangyan.xxp.yangyannew.mvp.ui.dialog.WarningDialog
import kotlinx.android.synthetic.main.activity_about.*
import javax.inject.Inject


/**
 * 如果没presenter
 * 你可以这样写
 *
 * @ActivityScope(請注意命名空間) class NullObjectPresenterByActivity
 * @Inject constructor() : IPresenter {
 * override fun onStart() {
 * }
 *
 * override fun onDestroy() {
 * }
 * }
 */
class AboutActivity : YangYanBaseActivity<AboutPresenter>(), AboutContract.View {
    @Inject
    lateinit var mWarningDialog: WarningDialog

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerAboutComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .aboutModule(AboutModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_about //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    private fun initToolbar() {
        setSupportActionBar(mToolbar)
        mToolbar.setNavigationOnClickListener {
            killMyself()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        mTvVersion.text = getVersionName(applicationContext)
        initToolbar()
        bindListener()
    }

    private fun bindListener() {
        mBtnLoginOut.onClick {
            mWarningDialog.apply {
                cancelButton("好的")
                confirmButton("不了") {
                    startActivity(Intent(applicationContext, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
                }
                content("啊哟,要不再看一会吧....")
                show(supportFragmentManager, "")
            }
        }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {
        finish()
    }

}
