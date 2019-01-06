package com.yangyan.xxp.yangyannew.mvp.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.app.getYangYanComponent
import com.yangyan.xxp.yangyannew.app.loadImage
import com.yangyan.xxp.yangyannew.app.onClick
import com.yangyan.xxp.yangyannew.app.visible
import com.yangyan.xxp.yangyannew.di.component.DaggerImageCollectionComponent
import com.yangyan.xxp.yangyannew.di.module.ImageCollectionModule
import com.yangyan.xxp.yangyannew.mvp.contract.ImageCollectionContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.presenter.ImageCollectionPresenter
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.ImageCollectionAdapter
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
     * 打开画廊页面
     */
    private val REQUSET_CODE_TO_GALLERY = 0x10
    @Inject
    @field:Named("ImageCollectionImagesAdapter")
    lateinit var mAdapter: ImageCollectionAdapter
    @Inject
    lateinit var mLayoutManager: GridLayoutManager

    //显示加载
    private var mShowLoader = true

    private val mImageInfo by lazy {
        intent.getSerializableExtra("data") as ImagesInfo
    }


    override fun showAddImageToFavoriteFailed() {

    }

    override fun showAddImageToFavoriteSuccess() {

    }

    /**
     * 是否已近收藏了该图集
     */
    override fun thisImageCollectIsFavorited(isFavorited: Boolean) {
        mIsFavorited = isFavorited
        mFabLikeOrDis.setImageResource(if (isFavorited) R.drawable.ic_delete else R.drawable.ic_like)
    }

    override fun setCoverImage(url: String) {
        initSlideHint()
        mIvCollectCover.loadImage(url, R.drawable.bg_loading_b)
    }

    override fun getContext(): Context = applicationContext

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerImageCollectionComponent.builder()
                .yangYanComponent(application.getYangYanComponent())
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
        mFabLikeOrDis.onClick {
            startActivity<FavoriteListActivity>("imageInfo" to mImageInfo)
            overridePendingTransition(R.anim.fragment_slide_up, 0)
        }
    }

    /**
     * 滑动的提示
     */
    private var mAnimationCount = 4

    private fun initSlideHint() {
        slideAnim(mAnimationCount, false)

    }

    private fun slideAnim(count: Int, slideDirection: Boolean) {
        val temp = !slideDirection
        mIvSwipeUp.animate()
                .translationYBy(if (temp) -500f else 500f)
                .setDuration(1000)
                .withEndAction {
                    if (count == 0) {
                        mIvSwipeUp.animate().alpha(0f)
                        return@withEndAction
                    }
                    slideAnim(count - 1, temp)
                }
    }


    //是否已收藏
    private var mIsFavorited = false

    private fun initToolbar() {
        mToolbar.title = mImageInfo.title
        setSupportActionBar(mToolbar)
        mToolbar.navigationIcon = resources.getDrawable(R.drawable.ic_back)
        mToolbar.setNavigationOnClickListener {
            killMyself()
        }

    }

    override fun onResume() {
        super.onResume()
        mPresenter?.getImageCollection(mImageInfo.id)
    }

    private fun initRecyclerView() {
        mRvImageCollection.layoutManager = mLayoutManager
        mRvImageCollection.adapter = mAdapter
        mAdapter.setOnItemClickListener { adapter, view, position ->
            this@ImageCollectionActivity.startActivityForResult<GalleryActivity>(
                    REQUSET_CODE_TO_GALLERY,
                    "id" to mImageInfo.id,
                    "index" to position)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            menuInflater.inflate(R.menu.meun_image_collect_list, it)
            (it.findItem(R.id.action_loading).icon as Animatable).start()
        }
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            it.findItem(R.id.action_loading).isVisible = mShowLoader
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun showLoading() {
        mShowLoader = true
        mFabLikeOrDis.visible(false)
        invalidateOptionsMenu()
    }

    override fun launchActivity(intent: Intent) {
    }

    override fun hideLoading() {
        mShowLoader = false
        mFabLikeOrDis.visible(true)
        invalidateOptionsMenu()
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