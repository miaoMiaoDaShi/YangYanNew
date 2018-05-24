package com.yangyan.xxp.yangyannew.mvp.contract

import android.content.Context
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.yangyan.xxp.yangyannew.mvp.model.entity.UserInfo
import io.reactivex.Observable

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description :个人页面
 */
interface MineContract {
    interface View : IView{
        fun getContext(): Context
        fun loadUserInfoSuccess(userInfo: UserInfo)
    }

    interface Model : IModel {
        /**
         * 加载个人信息
         */
        fun loadMineData(userInfo:UserInfo): Observable<UserInfo>

        /**
         * 加载收藏标签(简化   主要从数据库里获取)
         */
        fun loadCollectDataTag()

        /**
         * 加载收藏信息(依据分类)  简化  主要从数据库里获取
         */
        fun loadCollectDataDetailByTag(categoryIndex: Int)
    }
}