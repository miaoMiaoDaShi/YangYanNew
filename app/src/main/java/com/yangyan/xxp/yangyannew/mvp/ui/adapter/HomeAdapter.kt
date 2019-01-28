package com.yangyan.xxp.yangyannew.mvp.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yangyan.xxp.yangyannew.BuildConfig
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.app.YangYanImageConfig
import com.yangyan.xxp.yangyannew.app.loadImage
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.CropTransformation

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/22
 * Description :
 */
class HomeAdapter
constructor(val mDatas: MutableList<ImagesInfo>) : BaseQuickAdapter<ImagesInfo, BaseViewHolder>(R.layout.recycler_home_image, mDatas) {
    override fun convert(helper: BaseViewHolder, item: ImagesInfo) {
        helper.setText(R.id.mTvCount, "${item.imageCount}P")
                .setText(R.id.mTvTitle, item.title)
        if (BuildConfig.LOG_SHOW_IMAGE) {
            helper.getView<ImageView>(R.id.mIvImage).apply {
                post {
                    loadImage(YangYanImageConfig.Builder()
                            .bitmapTransformation(BlurTransformation(50))
                            .imageView(this)
                            .url(item.thumbSrc)
                            .placeholder(R.drawable.bg_loading)
                            .build())
                }

            }
            helper.getView<ImageView>(R.id.mCircleIvImage).apply {
                post {
                    loadImage(YangYanImageConfig.Builder()
                            .bitmapTransformation(CropTransformation(this.width, this.height, CropTransformation.CropType.TOP))
                            .imageView(this)
                            .url(item.thumbSrc)
                            .placeholder(R.drawable.bg_loading)
                            .build())
                }

            }


        }
    }


}