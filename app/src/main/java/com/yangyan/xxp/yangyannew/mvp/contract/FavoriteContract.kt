package com.yangyan.xxp.yangyannew.mvp.contract

import android.content.Context
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.yangyan.xxp.yangyannew.mvp.model.entity.FavoriteInfo
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.model.entity.UserInfo
import io.reactivex.Observable

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/29
 * Description :收藏相关
 */
interface FavoriteContract {
    interface View : IView {
        fun getContext(): Context
        /**
         * 收藏成功
         */
        fun showAddImageToFavoriteSuccess() {

        }

        /**
         * 收藏失败
         */
        fun showAddImageToFavoriteFailed() {

        }

        /**
         * 从选择的收藏夹中除该图集 成功
         */
        fun showDelImageToFavoriteSuccess() {

        }

        /**
         * 从选择的收藏夹中除该图集 失败
         */
        fun showDelImageToFavoriteFailed() {

        }

        /**
         * 是否有收藏的图片集合
         */
        fun favoriteImagesStatus(isEmpty: Boolean) {

        }
        /**
         * 该图集是否已近有收藏夹收藏了
         */
        fun thisImageCollectIsFavorited(isFavorited:Boolean){

        }

        /**
         * 加载收藏夹列表成功
         */
        fun onLoadFavorites(favorites: List<FavoriteInfo>){

        }
    }

    interface Model : IModel {
        /**
         * 获取收藏夹
         */
        fun getFavorite(): Observable<List<FavoriteInfo>>


        /**
         * 添加图集到收藏夹
         */
        fun addImageCollectToFavorite(favorites: List<FavoriteInfo>, imageCollect: ImagesInfo): Observable<String>

        /**
         * 删除对应收藏夹图集
         */
        fun delImageCollectByFavorite(favorites: List<FavoriteInfo>, imageCollect: ImagesInfo): Observable<String>

        /**
         * 根据收藏夹 获取收藏图解
         */
        fun getImageCollectByFavorite(favorite: FavoriteInfo): Observable<List<ImagesInfo>>


        /**
         * 删除收藏夹
         */
        fun delFavorite(favoriteInfo: FavoriteInfo, imageCollect: List<ImagesInfo>): Observable<Int>

        /**
         * 上传封面
         */
        fun uploadCover(imagePath: String, context: Context): Observable<String>

        /**
         * 添加收藏夹
         */
        fun addFavorite(favoriteInfo: FavoriteInfo): Observable<String>


        /**
         * 根据id查图集
         */
        fun getImageCollectById(id: Int): Observable<List<ImagesInfo>>

        fun getImageCollectByIdAndFavorite(favorites: List<FavoriteInfo>, id: Int): Observable<List<ImagesInfo>>

        fun getImageCollectByIdAndUser(id: Int, userInfo: UserInfo): Observable<List<ImagesInfo>>

        fun getFavoritesByImageCollectId(id: Int): Observable<List<FavoriteInfo>>
    }
}