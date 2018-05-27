package com.yangyan.xxp.yangyannew.app

import android.support.v4.app.FragmentManager
import com.yangyan.xxp.yangyannew.callback.onActivityBackCallback

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/26
 * Description :
 */
object FragmentBackHelper {
    fun HandleBack(fragmentManager: FragmentManager): Boolean {
        val fragments = fragmentManager.fragments
        fragments?.forEach {
            if (it is onActivityBackCallback) {
                return it.onBackPressed()
            }
        }

        return false
    }
}