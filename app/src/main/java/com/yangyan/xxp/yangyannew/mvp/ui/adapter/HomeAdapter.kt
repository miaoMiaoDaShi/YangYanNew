package com.yangyan.xxp.yangyannew.mvp.ui.adapter

import android.view.View
import android.widget.TextView
import com.jess.arms.base.BaseHolder
import com.jess.arms.base.DefaultAdapter
import com.jess.arms.http.imageloader.glide.ImageConfigImpl
import com.jess.arms.utils.ArmsUtils
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import org.jetbrains.anko.find
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/22
 * Description :
 */
class HomeAdapter
constructor(val mDatas: MutableList<ImagesInfo>) : DefaultAdapter<ImagesInfo>(mDatas) {
    override fun getLayoutId(viewType: Int): Int = R.layout.recycler_home_image

    override fun getHolder(v: View, viewType: Int): BaseHolder<ImagesInfo> =
            HomeHolder(v)

    companion object {
        class HomeHolder(itemView: View) : BaseHolder<ImagesInfo>(itemView) {
            override fun setData(data: ImagesInfo, position: Int) {
                itemView.find<TextView>(R.id.mTvCategory).text = data.category
                itemView.find<TextView>(R.id.mTvTitle).text = data.title
                ArmsUtils.obtainAppComponentFromContext(itemView.getContext())
                        .imageLoader().loadImage(itemView.context,
                                ImageConfigImpl
                                        .builder()
                                        .url(data.HDImageUrl)
                                        .imageView(itemView.find(R.id.mIvImage))
                                        .isCenterCrop(false)
                                        .build()
                        )
            }

        }
    }
}