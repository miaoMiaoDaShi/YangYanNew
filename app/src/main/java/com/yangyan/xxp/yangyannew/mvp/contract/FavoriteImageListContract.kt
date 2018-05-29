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
        override fun onUploadCoverFailed() {
            //空实现
        }

        override fun onUploadCoverSuccess(url: String) {
            //空实现
        }
    }

    interface Model : FavoriteContract.Model {

    }
}