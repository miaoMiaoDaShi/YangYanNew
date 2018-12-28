package com.yangyan.xxp.yangyannew.mvp.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yangyan.xxp.yangyannew.BuildConfig
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.app.YangYanImageConfig
import com.yangyan.xxp.yangyannew.app.loadImage

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/25
 * Description : 套图集合页面
 */

class ImageCollectionAdapter constructor(val mDatas: MutableList<String>) :
        BaseQuickAdapter<String, BaseViewHolder>(R.layout.recycler_image_collection, mDatas) {

    override fun convert(helper: BaseViewHolder, item: String) {
        helper.getView<ImageView>(R.id.mIvImage).apply {
            if (BuildConfig.LOG_SHOW_IMAGE) {
                loadImage(YangYanImageConfig.Builder()
                        .imageView(this)
                        .url(item)
                        .placeholder(R.drawable.bg_loading)
                        .build())

            }
        }

    }

    val ITEM_TYPE_A = 0x10
    val ITEM_TYPE_B = 0x11

    override fun getItemViewType(position: Int): Int {
       return  ITEM_TYPE_A
    }
}