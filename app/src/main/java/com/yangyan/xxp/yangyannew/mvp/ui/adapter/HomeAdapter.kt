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
import org.jetbrains.anko.find

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/22
 * Description :
 */
class HomeAdapter
constructor(val mDatas: MutableList<ImagesInfo>) : BaseQuickAdapter<ImagesInfo,BaseViewHolder>(R.layout.recycler_home_image,mDatas) {
    override fun convert(helper: BaseViewHolder, item: ImagesInfo) {
        helper.setText(R.id.mTvCategory,item.category)
                .setText(R.id.mTvTitle,item.title)
        if (BuildConfig.LOG_SHOW_IMAGE) {
            val imageView = helper.getView<ImageView>(R.id.mIvImage)
            imageView.find<ImageView>(R.id.mIvImage).apply {
                loadImage(YangYanImageConfig.Builder()
                        .imageView(this)
                        .url(item.HDImageUrl)
                        .placeholder(R.drawable.bg_loading)
                        .build())
            }
        }
    }


//    companion object {
//        class HomeHolder(itemView: View) : BaseHolder<ImagesInfo>(itemView) {
//            override fun setData(data: ImagesInfo, position: Int) {
//                itemView.find<TextView>().text = data.category
//                itemView.find<TextView>().text = data.title
//
//            }
//
//        }
//    }
}