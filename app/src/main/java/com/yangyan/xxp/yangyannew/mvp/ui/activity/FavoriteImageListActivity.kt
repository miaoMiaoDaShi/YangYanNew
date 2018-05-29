package com.yangyan.xxp.yangyannew.mvp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.di.component.DaggerFavoriteImageListComponent
import com.yangyan.xxp.yangyannew.di.module.FavoriteImageListModule
import com.yangyan.xxp.yangyannew.di.module.FavoriteModule
import com.yangyan.xxp.yangyannew.mvp.contract.FavoriteImageListContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.FavoriteInfo
import com.yangyan.xxp.yangyannew.mvp.presenter.FavoriteImageListPresenter
import kotlinx.android.synthetic.main.activity_favorite_image_list.*

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/29
 * Description : 图集的封面列表  和分类那个差不多 只不过 这个是个activity
 */
class FavoriteImageListActivity : BaseActivity<FavoriteImageListPresenter>()
        , FavoriteImageListContract.View {

//    /**
//     * objectId
//     */
//    private val mObjectId by lazy {
//        intent.getStringExtra("objectId")
//    }
//
//    /**
//     * 标题
//     */
//    private val mTitle by lazy {
//        intent.getStringExtra("title")
//    }
//
//    /**
//     * des  时间
//     */
//    private val mSubTitle by lazy {
//        intent.getStringExtra("subtitle")
//    }

    private val mFavorite by lazy {
        intent.getSerializableExtra("data") as FavoriteInfo
    }

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerFavoriteImageListComponent.builder()
                .appComponent(appComponent)
                .favoriteImageListModule(FavoriteImageListModule(this))
                .favoriteModule(FavoriteModule())
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int = R.layout.activity_favorite_image_list

    override fun initData(savedInstanceState: Bundle?) {
        mToolbar.title = mFavorite?.title
        mToolbar.subtitle = mFavorite?.createdAt
        mPresenter?.getImageCollectByFavorite(mFavorite)
    }

    override fun getContext(): Context = applicationContext

    override fun favoriteDataStatus(b: Boolean) {
    }


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
}