package com.yangyan.xxp.yangyannew.app

import android.content.Context
import com.jess.arms.base.BaseApplication
import com.jess.arms.utils.ArmsUtils
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.di.component.DaggerYangYanComponent
import com.yangyan.xxp.yangyannew.di.component.YangYanComponent
import com.yangyan.xxp.yangyannew.di.module.YangYanModule

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/11/23
 * Description :
 */
class YangYanApplication : BaseApplication(), YangYan {

    private lateinit var yangYanComponent: YangYanComponent
    override fun onCreate() {
        super.onCreate()

        yangYanComponent = DaggerYangYanComponent
                .builder()
                .appComponent(ArmsUtils.obtainAppComponentFromContext(this))
                .yangYanModule(YangYanModule())
                .build()
    }


    override fun yangYangComponent(): YangYanComponent {
        return yangYanComponent
    }
}