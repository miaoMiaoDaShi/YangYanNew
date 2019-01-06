package com.yangyan.xxp.yangyannew.mvp.contract

import android.content.Context
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import io.reactivex.Observable

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description :搜索
 */
interface SearchContract {
    interface View : IView{
        fun getContext(): Context
        fun startLoadMore()
        fun endLoadMore()
        fun searchResultStatus(isEmpty:Boolean)
    }

    interface Model : IModel {
        fun searchImagesByKeyword(pageIndex:Int, keyword: String):Observable<List<ImagesInfo>>
    }
}