package com.yangyan.xxp.yangyannew.mvp.contract

import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.yangyan.xxp.yangyannew.mvp.model.entity.FavoriteInfo
import io.reactivex.Observable

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/29
 * Description :收藏相关
 */
interface FavoriteContract {
    interface View : IView {
        /**
         * 收藏是否为空
         */
        fun favoriteDataStatus(b:Boolean)

        /**
         * 上传封面文件成功
         */
        fun onUploadCoverSuccess(url:String)

        /**
         * 上传封面文件失败
         */
        fun onUploadCoverFailed()
    }

    interface Model : IModel {
        /**
         * 获取收藏夹
         */
        fun getFavorite(): Observable<List<FavoriteInfo>>

        /**
         * 上传封面
         */
        fun uploadCover(imagePath:String):Observable<String>

        /**
         * 添加收藏夹
         */
        fun addFavorite(favoriteInfo: FavoriteInfo): Observable<String>
    }
}