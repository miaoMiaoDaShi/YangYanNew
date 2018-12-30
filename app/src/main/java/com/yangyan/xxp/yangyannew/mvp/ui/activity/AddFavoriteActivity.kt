package com.yangyan.xxp.yangyannew.mvp.ui.activity

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.yalantis.ucrop.UCrop
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.app.GlideImageEngine
import com.yangyan.xxp.yangyannew.app.getRealFilePath
import com.yangyan.xxp.yangyannew.app.getYangYanComponent
import com.yangyan.xxp.yangyannew.app.onClick
import com.yangyan.xxp.yangyannew.di.component.DaggerAddFavoriteComponent
import com.yangyan.xxp.yangyannew.di.module.AddFavoriteModule
import com.yangyan.xxp.yangyannew.mvp.contract.AddFavoriteContract
import com.yangyan.xxp.yangyannew.mvp.presenter.AddFavoritePresenter
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_add_favorite.*
import timber.log.Timber
import java.io.File
import java.net.URL

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/28
 * Description :
 */
class AddFavoriteActivity : BaseActivity<AddFavoritePresenter>(),
        AddFavoriteContract.View {
    override fun onUploadCoverSuccess(url: String) {
        mImageFilePath = url
        Toasty.success(applicationContext, "上传封面文件成功").show()
    }

    override fun onUploadCoverFailed() {
        mIvCover.setImageResource(R.drawable.ic_upload_img)
    }

    private var mImageFilePath = ""
    //知乎控件
    private val REQUEST_CODE_CHOOSE = 0x11

    override fun showLoading() {
        setStatusIsLoading(true)
    }

    private fun setStatusIsLoading(b: Boolean) {
        mProbar.visibility = if (b) View.VISIBLE else View.INVISIBLE
        mIvCover.isClickable = !b
        mBtnConfirm.isClickable = !b
        mEtTitle.isEnabled = !b
    }

    override fun launchActivity(intent: Intent) {
    }

    override fun hideLoading() {
        setStatusIsLoading(false)
    }

    override fun killMyself() {
        finish()
    }

    override fun showMessage(message: String) {
        Toasty.error(application, message).show()
    }

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerAddFavoriteComponent.builder()
                .yangYanComponent(application.getYangYanComponent())
                .addFavoriteModule(AddFavoriteModule(this))
                .build()
                .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int = R.layout.activity_add_favorite

    override fun initData(savedInstanceState: Bundle?) {
        setSupportActionBar(mToolbar)
        mToolbar.setNavigationOnClickListener {
            killMyself()
        }
        bindListener()
    }

    private fun bindListener() {
        mIvCover.onClick {
            Matisse.from(this)
                    .choose(MimeType.allOf())
                    .countable(true)
                    .capture(true)
                    .captureStrategy(CaptureStrategy(true, "com.upholstery.share.battery.fileprovider"))
                    .maxSelectable(1)
                    .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(GlideImageEngine())
                    .forResult(REQUEST_CODE_CHOOSE)
        }
        mBtnConfirm.onClick {
            mPresenter?.addFavorite(mImageFilePath, mEtTitle.text.toString().trim())
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK) {
            data?.let {
                val selectedImageUri = Matisse.obtainResult(it)[0]
                cropSelectImage(selectedImageUri)

            }
        } else if (requestCode==UCrop.REQUEST_CROP&&resultCode==Activity.RESULT_OK) {
            data?.let {
                val cropImageUri = UCrop.getOutput(it)
                mIvCover.setImageURI(cropImageUri)
                val imageFilePath = "${cropImageUri?.getRealFilePath(applicationContext)}"
                Timber.i(imageFilePath)
                mPresenter?.addFavoriteCover(imageFilePath, applicationContext)
            }

        }else if(requestCode==UCrop.REQUEST_CROP&&resultCode==UCrop.RESULT_ERROR) {
            data?.let {
                Timber.d(UCrop.getError(it))

            }
        }
    }

    /**
     * 图片裁剪
     */
    private fun cropSelectImage(selectedImageUri: Uri) {
        UCrop.of(selectedImageUri,
                Uri.parse("${
                        externalCacheDir
                        .absolutePath}${File.separator}cropImage.yy}"))
                .withOptions(UCrop.Options().apply {
                    setStatusBarColor(Color.WHITE)
                    setToolbarColor(Color.WHITE)
                    setToolbarWidgetColor(Color.BLACK)
                    setActiveWidgetColor(Color.RED) })
                .withAspectRatio(mIvCover.width.toFloat(), mIvCover.height.toFloat())
                .start(this)
    }
}