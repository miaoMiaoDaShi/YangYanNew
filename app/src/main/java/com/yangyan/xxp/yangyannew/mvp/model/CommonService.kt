package com.yangyan.xxp.yangyannew.mvp.model

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description :api
 */
interface CommonService {
    /**
     * 获取最新的套图推荐
     */
    @GET("new/page/{page}")
    fun getNewAtlasList(@Path("page") page: Int): Observable<ResponseBody>

    /**
     * 根据 id  获取详细的套图
     */
    @GET("/{id}")
    fun getAtlasDetailById(@Path("id") id: String): Observable<ResponseBody>

    /**
     * 根据分类
     */
    @GET("/cat/{category}")
    fun getAtlasListByCategory(@Path("category") category: String): Observable<ResponseBody>
}