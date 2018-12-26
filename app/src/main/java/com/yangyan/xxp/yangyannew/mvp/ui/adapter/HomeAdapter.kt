package com.yangyan.xxp.yangyannew.mvp.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jess.arms.base.BaseHolder
import com.jess.arms.base.DefaultAdapter
import com.jess.arms.utils.ArmsUtils
import com.yangyan.xxp.yangyannew.BuildConfig
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.app.YangYanImageConfig
import com.yangyan.xxp.yangyannew.app.loadImage
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import jp.wasabeef.glide.transformations.CropTransformation
import jp.wasabeef.glide.transformations.gpu.InvertFilterTransformation
import org.jetbrains.anko.find
import timber.log.Timber

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/22
 * Description :
 */
class HomeAdapter
constructor(val mDatas: MutableList<ImagesInfo>) : BaseQuickAdapter<ImagesInfo, BaseViewHolder>(R.layout.recycler_home_image, mDatas) {
    override fun convert(helper: BaseViewHolder, item: ImagesInfo) {
        helper.setText(R.id.mTvCategory, item.category)
                .setText(R.id.mTvTitle, item.title)
        if (BuildConfig.LOG_SHOW_IMAGE) {
            helper.getView<ImageView>(R.id.mIvImage).apply {
                post {
                    loadImage(YangYanImageConfig.Builder()
                            .bitmapTransformation(CropTransformation(this.width,this.height, CropTransformation.CropType.TOP))
                            .imageView(this)
                            .url(item.HDImageUrl)
                            .placeholder(R.drawable.bg_loading)
                            .build())
                }

            }


        }
    }


}