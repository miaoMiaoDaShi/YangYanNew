package com.yangyan.xxp.yangyannew.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/25
 * Description :
 */
class PhotoViewPager
constructor(context: Context, val attributeSet: AttributeSet) : ViewPager(context, attributeSet) {
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (e: IllegalArgumentException) {
            e.printStackTrace();
            return false;
        }
    }

}