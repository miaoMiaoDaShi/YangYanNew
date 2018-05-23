package com.yangyan.xxp.yangyannew.app

import android.app.Activity
import android.app.Application
import android.graphics.Color
import android.os.Bundle
import cn.bmob.v3.Bmob
import com.jaeger.library.StatusBarUtil
import com.yangyan.xxp.yangyannew.mvp.ui.activity.LoginActivity
import com.yangyan.xxp.yangyannew.mvp.ui.activity.SignUpActivity
import timber.log.Timber

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/22
 * Description :
 */
class ActivityLifecycleImpl : Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(activity: Activity?) {


    }

    override fun onActivityResumed(activity: Activity?) {
    }

    override fun onActivityStarted(activity: Activity?) {
    }

    override fun onActivityDestroyed(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        Timber.i("onActivityCreated")

        Bmob.initialize(activity, Constant.BMOB_APPLICATION_KEY)
        if (activity is LoginActivity || activity is SignUpActivity) {
            //StatusBarUtil.setDarkMode(activity)
            StatusBarUtil.setTranslucent(activity, 55)
        } else {
            StatusBarUtil.setLightMode(activity)
        }
    }
}