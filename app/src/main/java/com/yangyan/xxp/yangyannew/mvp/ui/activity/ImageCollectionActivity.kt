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
import com.yangyan.xxp.yangyannew.di.component.ImageCollectionComponent
import com.yangyan.xxp.yangyannew.di.module.ImageCollectionModule
import com.yangyan.xxp.yangyannew.mvp.contract.ImageCollectionContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.FavoriteInfo
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.presenter.ImageCollectionPresenter
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.ImageCollectionAdapter
import com.yangyan.xxp.yangyannew.mvp.ui.fragment.FavoriteBottomSheetFragment
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_image_collection.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import javax.inject.Inject
import javax.inject.Named

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/25
 * Description :  套图列表
 */
class ImageCollectionActivity : BaseActivity<ImageCollectionPresenter>(), ImageCollectionContract.View {

    /**
     * 收藏夹为空?
     */
    override fun favoriteDataStatus(b: Boolean) {


    }

    private val REQUSET_CODE_TO_GALLERY = 0x10
    @Inject
    @field:Named("ImageCollectionImagesAdapter")
    lateinit var mAdapter: ImageCollectionAdapter
    @Inject
    lateinit var mLayoutManager: GridLayoutManager
    @Inject
    lateinit var mFavoriteData: List<FavoriteInfo>

    private val mImageInfo by lazy {
        intent.getSerializableExtra("data") as ImagesInfo
    }

    private lateinit var mImageCollectionComponent: ImageCollectionComponent

    private val mFavoriteBottomSheetFragment by lazy {
        FavoriteBottomSheetFragment.newInstance().apply {
            setDoneBlock {
                mPresenter?.addImageCollectToFavorite(it,
                        mImageInfo
                )
            }
        }
    }

    override fun showAddImageToFavoriteFailed() {
        com.yangyan.xxp.yangyannew.app.dismissDialog(mFavoriteBottomSheetFragment)
    }

    override fun showAddImageToFavoriteSuccess() {
        com.yangyan.xxp.yangyannew.app.dismissDialog(mFavoriteBottomSheetFragment)
    }

    override fun getContext(): Context = applicationContext

    override fun setupActivityComponent(appComponent: AppComponent) {
        mImageCollectionComponent = DaggerImageCollectionComponent.builder()
                .appComponent(appComponent)
                .imageCollectionModule(ImageCollectionModule(this))
                .build()
        mImageCollectionComponent.inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_image_collection
    }

    override fun initData(savedInstanceState: Bundle?) {
        initRecyclerView()
        initToolbar()
        mIvCollectCover.loadImage(mImageInfo.HDImageUrl)
        mPresenter?.getIamgeCollection(mImageInfo.id)

        mFabLikeOrDis.onClick {
            if (mIsFavorite) {//收藏夹进来的
                Toasty.info(applicationContext, "想了一下,删除还是不要了吧.哈哈哈").show()

            } else {//普通进入
                mPresenter?.getFavoriteList()
                showDialog(mFavoriteBottomSheetFragment)
                mFavoriteBottomSheetFragment.setImageCollectionComponent(mImageCollectionComponent)
            }

        }
    }

    private val mIsFavorite by lazy {
        intent.getBooleanExtra("isFavorite", false)
    }

    private fun initToolbar() {
        //收藏夹进去的话 like变成del
        if (mIsFavorite) {
            mFabLikeOrDis.setImageResource(R.drawable.ic_delete)
        }
        setSupportActionBar(mToolbar)

        mToolbar.navigationIcon = resources.getDrawable(R.drawable.ic_back)
        mToolbar.setNavigationOnClickListener {
            killMyself()
        }

    }

    override fun onResume() {
        super.onResume()
        mToolbar.title = mImageInfo.category
        mToolbar.subtitle = mImageInfo.title
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
                        "id" to mImageInfo.id,
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
        if (requestCode == REQUSET_CODE_TO_GALLERY && resultCode == Activity.RESULT_OK) {
            mRvImageCollection.smoothScrollToPosition(data?.getIntExtra("index", 0) ?: 0)
        }
    }
}