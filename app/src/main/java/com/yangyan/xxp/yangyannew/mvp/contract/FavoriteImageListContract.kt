package com.yangyan.xxp.yangyannew.mvp.contract

import android.content.Context

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/29
 * Description :
 */
interface FavoriteImageListContract {
    interface View : FavoriteContract.View {
        fun getContext():Context
    }

    interface Model : FavoriteContract.Model {

    }
}