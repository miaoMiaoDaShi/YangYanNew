package com.yangyan.xxp.yangyannew.mvp.ui.adapter

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
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
import org.jetbrains.anko.find

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/25
 * Description : 套图集合页面
 */

class ImageCollectionAdapter constructor(val mDatas: MutableList<ImagesInfo>) :
        BaseQuickAdapter<ImagesInfo, BaseViewHolder>(R.layout.recycler_image_collection, mDatas) {

    override fun convert(helper: BaseViewHolder, item: ImagesInfo) {
        helper.getView<ImageView>(R.id.mIvImage).apply {
            if (BuildConfig.LOG_SHOW_IMAGE) {
                loadImage(YangYanImageConfig.Builder()
                        .imageView(this)
                        .url(item.displayImageUrl)
                        .placeholder(R.drawable.bg_loading)
                        .build())

            }
        }

    }

    val ITEM_TYPE_A = 0x10
    val ITEM_TYPE_B = 0x11

    override fun getItemViewType(position: Int): Int {
        val width = mDatas[position].width
        val height = mDatas[position].height
        return if (width > height) {
            ITEM_TYPE_A
        } else if (width < height) {
            ITEM_TYPE_B
        } else {
            ITEM_TYPE_B
        }
    }
}