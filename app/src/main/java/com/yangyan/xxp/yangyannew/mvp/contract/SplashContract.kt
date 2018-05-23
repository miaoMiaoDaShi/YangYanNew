package com.yangyan.xxp.yangyannew.mvp.contract

import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import io.reactivex.Observable

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/23
 * Description :启动页信息
 */
interface SplashContract {
    interface View :IView

    interface Model :IModel{
        //fun loadLocalUser():Observable<>
    }
}