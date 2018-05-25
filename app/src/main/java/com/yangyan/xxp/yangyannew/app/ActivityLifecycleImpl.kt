package com.yangyan.xxp.yangyannew.app

import android.app.Activity
import android.app.Application
import android.graphics.Color
import android.os.Bundle
import cn.bmob.v3.Bmob
import com.jaeger.library.StatusBarUtil
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.mvp.ui.activity.*
import org.jetbrains.anko.find
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
        activity?.let {
            when (it) {
                is LoginActivity, is SignUpActivity -> {
                    StatusBarUtil.setTranslucent(it, 55)
                }
                is SplashActivity -> StatusBarUtil.setTranslucent(it)
                is GalleryActivity,is ImageCollectionActivity -> {
                    StatusBarUtil.setTranslucent(it, 55)
                    StatusBarUtil.setLightMode(it)
            }
            else -> {
            StatusBarUtil.setLightMode(it)
        }
        }
    }

}

}