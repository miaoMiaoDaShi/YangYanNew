package com.yangyan.xxp.yangyannew.mvp.ui.adapter

import android.view.View
import android.widget.TextView
import com.jess.arms.base.BaseHolder
import com.jess.arms.base.DefaultAdapter
import com.jess.arms.http.imageloader.glide.ImageConfigImpl
import com.jess.arms.utils.ArmsUtils
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.mvp.model.entity.CollectInfo
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import org.jetbrains.anko.find
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/22
 * Description :我的页面 收藏
 */
class MineCollectAdapter
constructor(val mDatas: MutableList<CollectInfo>) : DefaultAdapter<CollectInfo>(mDatas) {
    override fun getLayoutId(viewType: Int): Int = R.layout.recycler_collect

    override fun getHolder(v: View, viewType: Int): BaseHolder<CollectInfo> =
            CollectHolder(v)

    companion object {
        class CollectHolder(itemView: View) : BaseHolder<CollectInfo>(itemView) {
            override fun setData(data: CollectInfo, position: Int) {
                itemView.find<TextView>(R.id.mTvCollectTitle).text = "测试"
                itemView.find<TextView>(R.id.mTvCollectDes).text = "2018-5-23  26个记录"
//                ArmsUtils.obtainAppComponentFromContext(itemView.getContext())
//                        .imageLoader().loadImage(itemView.context,
//                                ImageConfigImpl
//                                        .builder()
//                                        .url(data.HDImageUrl)
//                                        .imageView(itemView.find(R.id.mIvImage))
//                                        .isCenterCrop(false)
//                                        .build()
//                        )
            }

        }
    }
}