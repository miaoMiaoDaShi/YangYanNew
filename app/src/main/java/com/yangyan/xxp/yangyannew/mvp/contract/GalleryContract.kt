package com.yangyan.xxp.yangyannew.mvp.contract

import android.content.Context
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/25
 * Description :
 */
interface GalleryContract {
    interface View : IView {
        fun getContext():Context
        fun loadImageCollectionSuccess(date: List<String>)
    }

    interface Model : IModel
}