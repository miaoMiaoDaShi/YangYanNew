package com.yangyan.xxp.yangyannew.mvp.contract

import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.yangyan.xxp.yangyannew.mvp.model.entity.UserInfo
import io.reactivex.Observable

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/23
 * Description :注册
 */
interface SignUpContract {
    interface View : IView {
        fun signUpSuccess(userInfo: UserInfo)
    }

    interface Model : IModel {
        fun toSignUp(user: UserInfo): Observable<UserInfo>
    }
}