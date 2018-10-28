package com.yangyan.xxp.yangyannew.glide

import com.bumptech.glide.ListPreloader
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/10/28
 * Description : 提供列表中将显示资源的视图的大小。
 */
class PreloadSizeProvider :ListPreloader.PreloadSizeProvider<ImagesInfo>{
    override fun getPreloadSize(item: ImagesInfo, adapterPosition: Int, perItemPosition: Int): IntArray? {
        return intArrayOf(item.width,item.height)
    }
}