package com.yangyan.xxp.yangyannew.mvp.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
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
     * 收藏夹为空?
     */
    override fun favoriteDataStatus(b: Boolean) {


    }

    /**
     * 打开画廊页面
     */
    private val REQUSET_CODE_TO_GALLERY = 0x10
    @Inject
    @field:Named("ImageCollectionImagesAdapter")
    lateinit var mAdapter: ImageCollectionAdapter
    @Inject
    lateinit var mLayoutManager: GridLayoutManager

    private val mImageInfo by lazy {
        intent.getSerializableExtra("data") as ImagesInfo
    }


    override fun showAddImageToFavoriteFailed() {

    }

    override fun showAddImageToFavoriteSuccess() {

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
        initSlideHint()
        mIvCollectCover.loadImage(mImageInfo.thumbSrc)
        mPresenter?.getImageCollection(mImageInfo.id)

        mFabLikeOrDis.onClick {
            if (mIsFavorite) {//收藏夹进来的
                Toasty.info(applicationContext, "想了一下,删除还是不要了吧.哈哈哈").show()

            } else {//普通进入
                startActivity<FavoriteListActivity>("imageInfo" to mImageInfo)
                overridePendingTransition(R.anim.fragment_slide_up, 0)
            }

        }
    }

    /**
     * 滑动的提示
     */
    private var mAnimationCount = 4

    private fun initSlideHint() {
        slideAnim(mAnimationCount, false)

    }

    fun slideAnim(count: Int, slideDirection: Boolean) {
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
        supportActionBar!!.title = mImageInfo.title
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