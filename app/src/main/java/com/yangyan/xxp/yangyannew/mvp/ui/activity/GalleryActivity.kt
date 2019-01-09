package com.yangyan.xxp.yangyannew.mvp.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.app.getYangYanComponent
import com.yangyan.xxp.yangyannew.app.onClick
import com.yangyan.xxp.yangyannew.di.component.DaggerGalleryComponent
import com.yangyan.xxp.yangyannew.di.module.GalleryModule
import com.yangyan.xxp.yangyannew.mvp.contract.GalleryContract
import com.yangyan.xxp.yangyannew.mvp.presenter.GalleryPresenter
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.GalleryPageAdapter
import kotlinx.android.synthetic.main.activity_gallery.*
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description : 幻灯片播放页面
 */
class GalleryActivity : YangYanBaseActivity<GalleryPresenter>(), ViewPager.OnPageChangeListener
        , GalleryContract.View {


    @Inject
    lateinit var mAdapter: GalleryPageAdapter

    override fun loadImageCollectionSuccess(date: List<String>) {
        initViewPager()


    }

    override fun getContext() = applicationContext
    override fun showLoading() {
    }

    override fun launchActivity(intent: Intent) {
    }

    override fun hideLoading() {
    }

    override fun killMyself() {
        onBackPressed()
    }

    override fun showMessage(message: String) {
    }


    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerGalleryComponent.builder()
                .yangYanComponent(application.getYangYanComponent())
                .galleryModule(GalleryModule(this))
                .build()
                .inject(this)

    }

    override fun initView(savedInstanceState: Bundle?): Int = R.layout.activity_gallery

    override fun initData(savedInstanceState: Bundle?) {
//        mToolbar.setNavigationOnClickListener {
//            killMyself()
//        }
        mIvback.onClick { killMyself() }
        initViewPager()
        mPresenter?.getIamgeCollection(intent.getIntExtra("id",0))

    }

    private fun initViewPager() {
        val currentPosition  = intent.getIntExtra("index", 0)
        mVpGallery.adapter = mAdapter
        mVpGallery.offscreenPageLimit = 3
        mVpGallery.addOnPageChangeListener(this)
        mVpGallery.setCurrentItem(currentPosition)
        onPageSelected(currentPosition)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    @SuppressLint("SetTextI18n")
    override fun onPageSelected(position: Int) {
      mTvIndex.text = "${position + 1}/${mAdapter.mDatas.size}"
    }

    override fun onPageScrollStateChanged(state: Int) {


    }

    override fun onBackPressed() {
        val intent = Intent()
        setResult(Activity.RESULT_OK,intent.putExtra("index",mVpGallery.currentItem))
        super.onBackPressed()
    }

}