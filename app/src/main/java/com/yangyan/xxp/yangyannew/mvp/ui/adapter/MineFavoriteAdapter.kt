package com.yangyan.xxp.yangyannew.mvp.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.jess.arms.base.BaseHolder
import com.jess.arms.base.DefaultAdapter
import com.jess.arms.http.imageloader.glide.ImageConfigImpl
import com.jess.arms.utils.ArmsUtils
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.app.visible
import com.yangyan.xxp.yangyannew.di.scope.FavoriteScope
import com.yangyan.xxp.yangyannew.mvp.model.entity.FavoriteInfo
import org.jetbrains.anko.find
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/22
 * Description :我的页面 收藏
 */
class MineFavoriteAdapter
constructor(val mDatas: MutableList<FavoriteInfo>) : DefaultAdapter<FavoriteInfo>(mDatas) {
    override fun getLayoutId(viewType: Int): Int = R.layout.recycler_collect

    override fun getHolder(v: View, viewType: Int): BaseHolder<FavoriteInfo> =
            CollectHolder(v)


    private class CollectHolder(itemView: View) : BaseHolder<FavoriteInfo>(itemView) {
        override fun setData(data: FavoriteInfo, position: Int) {
            itemView.find<TextView>(R.id.mTvCollectTitle).text = data.title
            itemView.find<TextView>(R.id.mTvCollectDes).text = "创建时间: " + data.createdAt
            itemView.find<ImageView>(R.id.mIvCheckStatus).visible(data.isChecked)
            ArmsUtils.obtainAppComponentFromContext(itemView.getContext())
                    .imageLoader().loadImage(itemView.context,
                            ImageConfigImpl
                                    .builder()
                                    .url(data.coverUrl)
                                    .imageView(itemView.find(R.id.mIvCollectCover))
                                    .placeholder(R.drawable.bg_loading)
                                    .isCenterCrop(true)
                                    .build()
                    )
        }

    }
}