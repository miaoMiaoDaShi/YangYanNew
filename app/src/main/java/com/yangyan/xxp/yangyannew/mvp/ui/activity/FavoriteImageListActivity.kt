package com.yangyan.xxp.yangyannew.mvp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.yangyan.xxp.yangyannew.mvp.contract.FavoriteImageListContract
import com.yangyan.xxp.yangyannew.mvp.presenter.FavoriteImageListPresenter

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/29
 * Description : 图集的封面列表  和分类那个差不多 只不过 这个是个activity
 */
class FavoriteImageListActivity:BaseActivity<FavoriteImageListPresenter>()
,FavoriteImageListContract.View{
    override fun setupActivityComponent(appComponent: AppComponent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initData(savedInstanceState: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getContext(): Context {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun favoriteDataStatus(b: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onUploadCoverSuccess(url: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onUploadCoverFailed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun launchActivity(intent: Intent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun killMyself() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessage(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}