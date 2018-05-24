package com.yangyan.xxp.yangyannew.mvp.contract

import android.app.Activity
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.yangyan.xxp.yangyannew.mvp.model.entity.SplashImageInfo
import com.yangyan.xxp.yangyannew.mvp.model.entity.UserInfo
import io.reactivex.Observable

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/23
 * Description :启动页信息
 */
interface SplashContract {
    interface View :IView{
        fun getActivity():Activity
        fun alreadyHavePermission()
        fun countDown(count:Long)
    }

    interface Model :IModel{
        /**
         * 根据日期判断当前的图片是不是最新的,
         * 读取始终是读取的本地的  (会默默的下载一张图片)
         */
        fun loadSplashImage():Observable<SplashImageInfo>
    }
}