package com.yangyan.xxp.yangyannew.mvp.contract

import android.content.Context
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.yangyan.xxp.yangyannew.mvp.model.entity.FavoriteInfo
import com.yangyan.xxp.yangyannew.mvp.model.entity.MineZipInfo
import com.yangyan.xxp.yangyannew.mvp.model.entity.UserInfo
import io.reactivex.Observable

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description :个人页面
 */
interface MineContract {
    interface View : FavoriteContract.View {
        fun loadUserInfoSuccess(userInfo: UserInfo)
    }

    interface Model : FavoriteContract.Model {
        /**
         * 加载个人页面数据 数据是和收藏列表整合的
         */
        fun loadMineData(userInfo: UserInfo): Observable<MineZipInfo>

        /**
         * 更改个人信息
         */
        fun updateUserInfo(userInfo: UserInfo): Observable<Int>

        /**
         * 获取个人信息
         */
        fun loadUserInfo(userInfo: UserInfo): Observable<UserInfo>
    }
}