package com.yangyan.xxp.yangyannew.mvp.contract

import android.content.Context
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import io.reactivex.Observable

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/25
 * Description : 套图页面,因为要使用收藏功能,索性直接继承了
 */
interface ImageCollectionContract {
    interface View :FavoriteContract.View{
        fun setCoverImage(url:String)
    }
    interface Model:FavoriteContract.Model{
        fun getImageCollection(id:Int): Observable<List<String>>
    }
}