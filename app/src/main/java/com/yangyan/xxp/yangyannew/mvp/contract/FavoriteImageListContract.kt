package com.yangyan.xxp.yangyannew.mvp.contract

import android.content.Context
import com.yangyan.xxp.yangyannew.mvp.model.entity.FavoriteInfo
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import io.reactivex.Observable

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/29
 * Description :
 */
interface FavoriteImageListContract {
    interface View : FavoriteContract.View


    interface Model : FavoriteContract.Model

}