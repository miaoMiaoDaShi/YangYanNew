package com.yangyan.xxp.yangyannew.mvp.contract

import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.yangyan.xxp.yangyannew.mvp.model.entity.UserInfo
import io.reactivex.Observable

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/23
 * Description :登录
 */
interface LoginContract {
    interface View : IView {
        fun loginSuccess(userInfo: UserInfo)
    }

    interface Model : IModel {
        fun toLogin(userInfo: UserInfo): Observable<UserInfo>
    }
}