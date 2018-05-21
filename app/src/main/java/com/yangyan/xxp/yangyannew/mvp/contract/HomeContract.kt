package com.yangyan.xxp.yangyannew.mvp.contract

import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import io.reactivex.Observable

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description :主页
 */
interface HomeContract {
    interface View : IView

    interface Model : IModel {
        //首页的信息 获取的主要是最新的图集
        fun getHomeData(pageIndex: Int): Observable<List<ImagesInfo>>
    }
}