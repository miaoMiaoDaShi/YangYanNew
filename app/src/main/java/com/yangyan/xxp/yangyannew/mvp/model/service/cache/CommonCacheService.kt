package com.yangyan.xxp.yangyannew.mvp.model.service.cache

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
     * 最新的套圖
     */
    @LifeCache(duration = CACHE_LONG,timeUnit = TimeUnit.MINUTES)
    fun getNewAtlasList(newAtlasList:Observable<String>,key: DynamicKey):Observable<Reply<String>>


    /**
     * 根據id  獲取圖集
     */
    @LifeCache(duration = CACHE_LONG,timeUnit = TimeUnit.MINUTES)
    fun getAtlasDetailById(atlasDetailById:Observable<String>,key: DynamicKey):Observable<Reply<String>>

    /**
     * 获取标志的套图推荐
     */
    @LifeCache(duration = CACHE_LONG,timeUnit = TimeUnit.MINUTES)
    fun getTagAtlasList(tagAtlasList:Observable<String>,key: DynamicKey):Observable<Reply<String>>

    /**
     * 根据分类
     */
    @LifeCache(duration = CACHE_LONG,timeUnit = TimeUnit.MINUTES)
    fun getAtlasListByCategory(atlasListByCategory:Observable<String>,keyGroup: DynamicKeyGroup):Observable<Reply<String>>



}