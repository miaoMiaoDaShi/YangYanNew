package com.yangyan.xxp.yangyannew.mvp.contract

import android.content.Context
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.yangyan.xxp.yangyannew.mvp.model.entity.FavoriteInfo
import com.yangyan.xxp.yangyannew.mvp.model.entity.UserInfo
import io.reactivex.Observable

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description :个人页面
 */
interface MineContract {
    interface View : FavoriteContract.View{
        fun getContext(): Context
        fun loadUserInfoSuccess(userInfo: UserInfo)
        override fun onUploadCoverFailed() {
            //空实现
        }

        override fun onUploadCoverSuccess(url: String) {
            //空实现
        }

    }

    interface Model : FavoriteContract.Model {
        /**
         * 加载个人信息
         */
        fun loadMineData(userInfo:UserInfo): Observable<UserInfo>

    }
}