package com.yangyan.xxp.yangyannew.app

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/23
 * Description :
 */
/**
 * 点击事件扩展方法
 */
fun View.onClick(method: () -> Unit): View {
    setOnClickListener { method.invoke() }
    return this
}

/**
 * 点击事件扩展方法
 */
fun View.onClick(listener: View.OnClickListener): View {
    setOnClickListener(listener)
    return this
}

/**
 * 设置View的可见
 */
fun View.visible(isVisible: Boolean): View {
    visibility = if (isVisible) VISIBLE else GONE
    return this
}