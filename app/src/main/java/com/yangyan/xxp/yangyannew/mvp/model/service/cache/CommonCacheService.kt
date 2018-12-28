package com.yangyan.xxp.yangyannew.mvp.model.service.cache

import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesDetailInfo
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.DynamicKeyGroup
import io.rx_cache2.LifeCache
import io.rx_cache2.Reply
import java.util.concurrent.TimeUnit

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/6/8
 * Description : 緩存--RxCache
 */
interface CommonCacheService {
    companion object {
        /**
         * 緩存有效時間  30分鐘
         */
        const val CACHE_LONG = 30L
    }


    /**
     * 根据key获取图片
     */
    @LifeCache(duration = CACHE_LONG, timeUnit = TimeUnit.MINUTES)
    fun getImagesByKey(newAtlasList: Observable<List<ImagesInfo>>, key: DynamicKey): Observable<Reply<List<ImagesInfo>>>

    /**
     * 根據id  獲取圖集
     */
    @LifeCache(duration = CACHE_LONG, timeUnit = TimeUnit.MINUTES)
    fun getImagesDetailById(atlasDetailById: Observable<ImagesDetailInfo>, key: DynamicKey): Observable<Reply<ImagesDetailInfo>>

    /**
     * 根据分类
     */
    @LifeCache(duration = CACHE_LONG, timeUnit = TimeUnit.MINUTES)
    fun getImagesByCategory(atlasListByCategory: Observable<List<ImagesInfo>>, keyGroup: DynamicKeyGroup): Observable<Reply<List<ImagesInfo>>>


}