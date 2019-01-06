package com.yangyan.xxp.yangyannew.mvp.contract

import android.content.Context
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.yangyan.xxp.yangyannew.mvp.model.entity.FavoriteInfo
import io.reactivex.Observable

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/28
 * Description : 添加收藏夹
 */
interface AddFavoriteContract {
    interface View : FavoriteContract.View{
         fun onUploadCoverFailed() {
            //空实现
        }
         fun onUploadCoverSuccess(url: String) {
            //空实现
        }

    }

    interface Model : FavoriteContract.Model {


    }
}