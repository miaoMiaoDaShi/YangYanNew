package com.yangyan.xxp.yangyannew.mvp.contract

import android.content.Context
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.yangyan.xxp.yangyannew.mvp.model.entity.CategoryInfo
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.model.parser.IParse
import io.reactivex.Observable

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description :分类子页面
 */
interface CategoryChildContract {
    interface View : IView {
        fun getContext(): Context
        fun startLoadMore()
        fun endLoadMore()
    }

    interface Model : IModel {
        fun getCategoryChildData(categoryCode:String,pageIndex: Int): Observable<List<ImagesInfo>>
    }
}