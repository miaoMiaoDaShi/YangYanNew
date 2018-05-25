package com.yangyan.xxp.yangyannew.mvp.contract

import android.content.Context
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import io.reactivex.Observable

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/25
 * Description : 套图页面
 */
interface ImageCollectionContract {
    interface View :IView{
        fun getContext():Context
    }
    interface Model:IModel{
        fun getIamgeCollection(id:String): Observable<List<ImagesInfo>>
    }
}