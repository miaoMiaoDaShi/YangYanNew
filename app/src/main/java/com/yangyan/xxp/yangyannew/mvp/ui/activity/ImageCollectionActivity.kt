package com.yangyan.xxp.yangyannew.mvp.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.app.loadImage
import com.yangyan.xxp.yangyannew.app.onClick
import com.yangyan.xxp.yangyannew.app.showDialog
import com.yangyan.xxp.yangyannew.app.visible
import com.yangyan.xxp.yangyannew.di.component.DaggerImageCollectionComponent
import com.yangyan.xxp.yangyannew.di.module.ImageCollectionModule
import com.yangyan.xxp.yangyannew.mvp.contract.ImageCollectionContract
import com.yangyan.xxp.yangyannew.mvp.presenter.ImageCollectionPresenter
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.ImageCollectionAdapter
import com.yangyan.xxp.yangyannew.mvp.ui.fragment.FavoriteBottomSheetFragment
import kotlinx.android.synthetic.main.activity_image_collection.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/25
 * Description :  套图列表
 */
class ImageCollectionActivity : BaseActivity<ImageCollectionPresenter>(), ImageCollectionContract.View {

    private val REQUSET_CODE_TO_GALLERY = 0x10
    @Inject
    lateinit var mAdapter: ImageCollectionAdapter
    @Inject
    lateinit var mLayoutManager: GridLayoutManager

    private val mFavoriteBottomSheetFragment by lazy {
        FavoriteBottomSheetFragment.newInstance()
    }

    override fun getContext(): Context = applicationContext

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerImageCollectionComponent.builder()
                .appComponent(appComponent)
                .imageCollectionModule(ImageCollectionModule(this))
                .build()
                .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_image_collection
    }

    override fun initData(savedInstanceState: Bundle?) {
        initRecyclerView()
        initToolbar()
        mIvCollectCover.loadImage(intent.getStringExtra("cover"))
        mPresenter?.getIamgeCollection(intent.getStringExtra("id"))

        mFabLikeOrDis.onClick {
            showDialog(mFavoriteBottomSheetFragment)
        }
    }

    private fun initToolbar() {
        setSupportActionBar(mToolbar)

        mToolbar.navigationIcon = resources.getDrawable(R.drawable.ic_back)
        mToolbar.setNavigationOnClickListener {
            killMyself()
        }

    }

    override fun onResume() {
        super.onResume()
        mToolbar.title = intent.getStringExtra("category")
        mToolbar.subtitle = intent.getStringExtra("title")
        mToolbar.setSubtitleTextColor(Color.BLACK)
    }

    private fun initRecyclerView() {
        mLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (mAdapter.getItemViewType(position)) {
                    mAdapter.ITEM_TYPE_A -> 2
                    mAdapter.ITEM_TYPE_B -> 1
                    else -> {
                        1
                    }
                }
            }
        }
        mRvImageCollection.layoutManager = mLayoutManager
        mRvImageCollection.adapter = mAdapter
        mAdapter.setOnItemClickListener { view, viewType, data, position ->
            kotlin.run {
                this@ImageCollectionActivity.startActivityForResult<GalleryActivity>(
                        REQUSET_CODE_TO_GALLERY,
                        "id" to intent.getStringExtra("id"),
                        "index" to position
                )
            }
        }
    }

    override fun showLoading() {
        mProbar.visible(true)
    }

    override fun launchActivity(intent: Intent) {
    }

    override fun hideLoading() {
        mProbar.visible(false)

    }

    override fun killMyself() {
        finish()
    }

    override fun showMessage(message: String) {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==REQUSET_CODE_TO_GALLERY&&resultCode==Activity.RESULT_OK){
            mRvImageCollection.smoothScrollToPosition(data?.getIntExtra("index",0)?:0)
        }
    }
}