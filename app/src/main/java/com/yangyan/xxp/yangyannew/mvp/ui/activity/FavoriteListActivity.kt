package com.yangyan.xxp.yangyannew.mvp.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.View
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.app.getYangYanComponent
import com.yangyan.xxp.yangyannew.app.onClick
import com.yangyan.xxp.yangyannew.app.visible
import com.yangyan.xxp.yangyannew.di.component.DaggerFavoriteImageListComponent
import com.yangyan.xxp.yangyannew.di.component.DaggerFavoriteListComponent
import com.yangyan.xxp.yangyannew.di.component.DaggerImageCollectionComponent
import com.yangyan.xxp.yangyannew.di.module.FavoriteListModule
import com.yangyan.xxp.yangyannew.di.module.FavoriteModule
import com.yangyan.xxp.yangyannew.di.module.ImageCollectionModule
import com.yangyan.xxp.yangyannew.mvp.contract.FavoriteContract
import com.yangyan.xxp.yangyannew.mvp.contract.FavoriteListContract
import com.yangyan.xxp.yangyannew.mvp.contract.ImageCollectionContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.FavoriteInfo
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.presenter.FavoriteListPresenter
import com.yangyan.xxp.yangyannew.mvp.presenter.FavoritePresenter
import com.yangyan.xxp.yangyannew.mvp.presenter.ImageCollectionPresenter
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.MineFavoriteAdapter
import kotlinx.android.synthetic.main.activity_favorite_list.*
import org.jetbrains.anko.startActivity
import timber.log.Timber
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/6/2
 * Description : 收藏夹列表页面 点击收藏进入
 */
class FavoriteListActivity : BaseActivity<FavoriteListPresenter>()
        , FavoriteListContract.View {
    override fun getContext(): Context {
        return super.getApplicationContext()
    }

    @Inject
    lateinit var mAdapter: MineFavoriteAdapter
    @Inject
    lateinit var mLayoutManager: LinearLayoutManager

    private var mOriginalFavorite = mutableListOf<FavoriteInfo>()
    private val mCheckedFavorite = mutableListOf<FavoriteInfo>()
    private val mUnCheckedFavorite = mutableListOf<FavoriteInfo>()

    private val mImagesInfo by lazy {
        intent.getSerializableExtra("imageInfo") as ImagesInfo
    }

    //显示加载
    private var mShowLoader = true

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerFavoriteListComponent.builder()
                .yangYanComponent(application.getYangYanComponent())
                .favoriteListModule(FavoriteListModule(this))
                .build().inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int = R.layout.activity_favorite_list

    override fun initData(savedInstanceState: Bundle?) {
        initToolbar()
        initRecyclerView()
        bindListener()

    }

    override fun onResume() {
        super.onResume()
        mPresenter?.getFavoriteListWithStatus(mImagesInfo.id)
    }

    private fun initToolbar() {
        setSupportActionBar(mToolbar)
        mToolbar.setNavigationOnClickListener {
            killMyself()
        }

        mToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_add -> {
                    startActivity<AddFavoriteActivity>()
                }
                else -> {
                }

            }
            false
        }
    }


    private fun bindListener() {
        mTvDone.onClick {
            mPresenter?.addWithDelImageCollectToFavorite(mCheckedFavorite, mUnCheckedFavorite, mImagesInfo)
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
                        if (data.isChecked && !mOriginalFavorite[position].isChecked) {//当前选中 原始未选择
                            if (!mCheckedFavorite.contains(data)) {
                                mCheckedFavorite.add(data)
                            }
                        } else if (!data.isChecked && !mOriginalFavorite[position].isChecked) {//当前未选中,原始未选中
                            mCheckedFavorite.remove(data)
                        } else if (!data.isChecked && mOriginalFavorite[position].isChecked) {//当前未选中 原始选中
                            if (!mUnCheckedFavorite.contains(data)) {
                                mUnCheckedFavorite.add(data)
                            }
                        } else if (data.isChecked && mOriginalFavorite[position].isChecked) {//当前选中,原始选中
                            mUnCheckedFavorite.remove(data)
                        }
                        mAdapter.notifyItemChanged(position)
                        mTvDone.isEnabled = mCheckedFavorite.isNotEmpty() || mUnCheckedFavorite.isNotEmpty()
                    }

                }
            }
        }
    }


    override fun onLoadFavorites(favorites: List<FavoriteInfo>) {
        favorites.forEach { mOriginalFavorite.add(it.clone()) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            menuInflater.inflate(R.menu.meun_favorite_list, it)
            (it.findItem(R.id.action_loading).icon as Animatable).start()
        }
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            it.findItem(R.id.action_add).isVisible = !mShowLoader
            it.findItem(R.id.action_loading).isVisible = mShowLoader
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun showLoading() {
        mShowLoader = true
        invalidateOptionsMenu()
        mTvDone.isEnabled = false
    }

    override fun launchActivity(intent: Intent) {
    }

    override fun hideLoading() {
        mShowLoader = false
        invalidateOptionsMenu()
        mTvDone.isEnabled = mCheckedFavorite.isNotEmpty() || mUnCheckedFavorite.isNotEmpty()
    }

    override fun killMyself() {
        finish()
    }

    override fun showMessage(message: String) {
    }


    override fun finish() {
        super.finish()
        overridePendingTransition(0, R.anim.fragment_slide_down)
    }

}