package com.yangyan.xxp.yangyannew.mvp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.app.getYangYanComponent
import com.yangyan.xxp.yangyannew.app.visible
import com.yangyan.xxp.yangyannew.di.component.DaggerFavoriteImageListComponent
import com.yangyan.xxp.yangyannew.di.module.FavoriteImageListModule
import com.yangyan.xxp.yangyannew.di.module.FavoriteModule
import com.yangyan.xxp.yangyannew.mvp.contract.FavoriteImageListContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.FavoriteInfo
import com.yangyan.xxp.yangyannew.mvp.presenter.FavoriteImageListPresenter
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.HomeAdapter
import com.yangyan.xxp.yangyannew.mvp.ui.dialog.WarningDialog
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.activity_favorite_image_list.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import javax.inject.Inject
import javax.inject.Named

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/29
 * Description : 图集的封面列表  和分类那个差不多 只不过 这个是个activity
 */
class FavoriteImageListActivity : YangYanBaseActivity<FavoriteImageListPresenter>()
        , FavoriteImageListContract.View, SwipeRefreshLayout.OnRefreshListener {



    @Inject
    @field:Named("FavoriteImagesAdapter")
    lateinit var mAdapter: HomeAdapter
    @Inject
    lateinit var mLayoutManager: LinearLayoutManager
    private val mFavorite by lazy {
        intent.getSerializableExtra("data") as FavoriteInfo
    }
    @Inject
    lateinit var mWarningDialog: WarningDialog

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerFavoriteImageListComponent.builder()
                .yangYanComponent(application.getYangYanComponent())
                .favoriteImageListModule(FavoriteImageListModule(this))
                .favoriteModule(FavoriteModule())
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int = R.layout.activity_favorite_image_list

    override fun initData(savedInstanceState: Bundle?) {
        initToolbar()
        initRecyclerView()
        mSwipeRefreshLayout.setOnRefreshListener(this)

        mSwipeRefreshLayout.post {
            mSwipeRefreshLayout.isRefreshing = true
            onRefresh()
        }

    }

    private fun initToolbar() {
        mToolbar.title = mFavorite?.title
        mToolbar.subtitle = mFavorite?.createdAt
        setSupportActionBar(mToolbar)
        mToolbar.setNavigationOnClickListener {
            killMyself()
        }
        mToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_delete -> {
                    delFavorite()
                }
                else -> {
                }

            }
            false
        }
    }

    /**
     * 删除该收藏夹
     */
    private fun delFavorite() {
        mWarningDialog.apply {
            cancelButton("点错了")
            confirmButton("就这么任性") {
                mPresenter?.delFavorite(mFavorite)
            }
            content("哟,好好的删了干嘛啊?")
            des("删除后,该收藏夹的图片会移动到默认分组哦!")
            show(supportFragmentManager, "")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.meun_favorite_image_list, menu)
        //判断是不是默认收藏夹
        if (mFavorite.isDefault) {
            menu?.removeItem(R.id.action_delete)
        }
        return true
    }

    override fun onRefresh() {
        mPresenter?.getImageCollectByFavorite(mFavorite)

    }

    private fun initRecyclerView() {
        mRvImageCover.layoutManager = mLayoutManager
        mRvImageCover.adapter = mAdapter.apply {
            setOnItemClickListener { view, viewType, position ->
                kotlin.run {
                    startActivity<ImageCollectionActivity>(
                            "data" to data[position],
                            "isFavorite" to true//收藏夹进去的????
                    )
                }
            }
        }

    }

    /**
     * 是否有收藏的图片集合
     * @param isEmpty true 为空
     */
    override fun favoriteImagesStatus(isEmpty: Boolean) {
        mTvCollectIsEmpty.visible(isEmpty)
    }
    override fun getContext(): Context = applicationContext


    override fun showLoading() {
    }

    override fun launchActivity(intent: Intent) {
    }

    override fun hideLoading() {
        mSwipeRefreshLayout.isRefreshing = false
    }

    override fun killMyself() {
        finish()
    }

    override fun showMessage(message: String) {

    }
}