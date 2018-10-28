package com.yangyan.xxp.yangyannew.glide

import android.app.Activity
import com.bumptech.glide.Glide
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions
import com.jess.arms.http.imageloader.glide.GlideRequests
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/10/28
 * Description :提供应该预加载的数据源。
 */
class PerloadModelProvider(val mImageInfos: List<ImagesInfo>, val mActivity: Activity) : ListPreloader.PreloadModelProvider<ImagesInfo> {

    override fun getPreloadItems(position: Int): MutableList<ImagesInfo> {
        return mutableListOf(mImageInfos[position])

    }

    override fun getPreloadRequestBuilder(item: ImagesInfo): RequestBuilder<*>? {
        return Glide
                .with(mActivity)
                .load(item.displayImageUrl)
                .apply(RequestOptions()
                        .override(item.width, item.height)
                        .placeholder(R.drawable.bg_loading))
    }
}