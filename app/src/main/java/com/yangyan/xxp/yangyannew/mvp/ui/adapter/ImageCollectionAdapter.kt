package com.yangyan.xxp.yangyannew.mvp.ui.adapter

import android.view.View
import android.widget.ImageView
import com.jess.arms.base.BaseHolder
import com.jess.arms.base.DefaultAdapter
import com.jess.arms.http.imageloader.glide.ImageConfigImpl
import com.jess.arms.utils.ArmsUtils
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.app.loadImage
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import org.jetbrains.anko.find

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/25
 * Description : 套图集合
 */

class ImageCollectionAdapter constructor(val mDatas: MutableList<ImagesInfo>) : DefaultAdapter<ImagesInfo>(mDatas) {
     val ITEM_TYPE_A = 0x10
     val ITEM_TYPE_B = 0x11

    override fun getLayoutId(viewType: Int): Int = R.layout.recycler_image_collection


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

    override fun getHolder(v: View, viewType: Int): BaseHolder<ImagesInfo> =
            ImageCollectionHolder(v)

    companion object {
        class ImageCollectionHolder(itemView: View) : BaseHolder<ImagesInfo>(itemView) {
            override fun setData(data: ImagesInfo, position: Int) {
                itemView.find<ImageView>(R.id.mIvImage).loadImage(data.displayImageUrl)

            }

        }
    }
}