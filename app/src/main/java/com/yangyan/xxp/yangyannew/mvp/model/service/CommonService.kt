package com.yangyan.xxp.yangyannew.mvp.model.service

import com.yangyan.xxp.yangyannew.app.Constant
import com.yangyan.xxp.yangyannew.mvp.model.entity.SplashImageInfo
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

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
     * 获取标志的套图推荐
     */
    @GET("tag/page/{page}")
    fun getTagAtlasList(@Path("page") page: Int): Observable<ResponseBody>

    /**
     * 根据分类
     */
    @GET("/cat/{category}/page/{page}")
    fun getAtlasListByCategory(
            @Path("category") category: String,
            @Path("page") page: Int
    ): Observable<ResponseBody>

    /**
     * 启动页的图片  来自gank
     */
    @GET()
    fun getSplashImage(@Url url: String = Constant.SPLASH_IMAGE_URL): Observable<SplashImageInfo>

    /**
     * 下载图片
     */
    @GET()
    fun download(@Url url: String): Observable<ResponseBody>


}