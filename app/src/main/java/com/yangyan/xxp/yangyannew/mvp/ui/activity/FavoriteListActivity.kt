package com.yangyan.xxp.yangyannew.mvp.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.app.onClick
import com.yangyan.xxp.yangyannew.di.component.DaggerImageCollectionComponent
import com.yangyan.xxp.yangyannew.di.module.ImageCollectionModule
import com.yangyan.xxp.yangyannew.mvp.contract.ImageCollectionContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.FavoriteInfo
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.presenter.ImageCollectionPresenter
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.MineFavoriteAdapter
import kotlinx.android.synthetic.main.activity_favorite_list.*
import timber.log.Timber
import java.io.Serializable
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/6/2
 * Description : 收藏夹列表页面 点击收藏进入
 */
class FavoriteListActivity : BaseActivity<ImageCollectionPresenter>()
        , ImageCollectionContract.View {

    @Inject
    lateinit var mAdapter: MineFavoriteAdapter
    @Inject
    lateinit var mLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mFavoriteDatas: List<FavoriteInfo>

    private val mCheckedFavorite = mutableListOf<FavoriteInfo>()


    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerImageCollectionComponent.builder()
                .appComponent(appComponent)
                .imageCollectionModule(ImageCollectionModule(this))
                .build().inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int = R.layout.activity_favorite_list

    override fun initData(savedInstanceState: Bundle?) {
        initToolbar()
        initRecyclerView()
        bindListener()
        mPresenter?.getFavoriteList()
    }

    private fun initToolbar() {
        setSupportActionBar(mToolbar)
        mToolbar.setNavigationOnClickListener {
            killMyself()
        }
    }

    private fun bindListener() {
        mTvDone.onClick {
            mPresenter?.addImageCollectToFavorite(mFavoriteDatas, intent.getSerializableExtra("imageInfo") as ImagesInfo)
        }

    }

    private fun initRecyclerView() {
        mRvFavorite.apply {
            layoutManager = mLayoutManager
            adapter = mAdapter.apply {
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        outRect.top = ArmsUtils.dip2px(applicationContext, 10f)
                        outRect.left = ArmsUtils.dip2px(applicationContext, 10f)
                        outRect.right = ArmsUtils.dip2px(applicationContext, 10f)
                        if (parent.getChildLayoutPosition(view) == parent.layoutManager!!.childCount) {
                            outRect.bottom = ArmsUtils.dip2px(applicationContext, 10f)
                        }
                    }
                })

                setOnItemClickListener { view, viewType, data, position ->
                    kotlin.run {
                        data as FavoriteInfo
                        data.isChecked = !data.isChecked
                        if (data.isChecked) {
                            if (!mCheckedFavorite.contains(data)) {
                                mCheckedFavorite.add(data)
                            }
                        } else {
                            if (mCheckedFavorite.contains(data)) {
                                mCheckedFavorite.remove(data)
                            }
                        }
                        mAdapter.notifyItemChanged(position)
                        mTvDone.isEnabled = mFavoriteDatas.isNotEmpty()
                    }
                }
            }
        }
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
        finish()
    }

    override fun showMessage(message: String) {
    }



    override fun finish() {
        super.finish()
        overridePendingTransition(0,R.anim.fragment_slide_down)
    }

}