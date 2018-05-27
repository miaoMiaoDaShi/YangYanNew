package com.yangyan.xxp.yangyannew.app

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.constraint.Placeholder
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.jess.arms.http.imageloader.ImageConfig
import com.jess.arms.http.imageloader.glide.ImageConfigImpl
import com.jess.arms.utils.ArmsUtils
import com.yangyan.xxp.yangyannew.R
import org.jetbrains.anko.find
import java.io.File

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

fun AppCompatActivity.showDialog(dialog: DialogFragment)  {
    dialog.show(supportFragmentManager, "TAG")
}

fun Fragment.showDialog(dialog: DialogFragment) {
    dialog.show(fragmentManager, "TAG")
}

fun dismissDialog(dialog: DialogFragment) {
    dialog.dismiss()
}

/**
 *
 */
fun <T: ImageConfig> ImageView.loadImage(config:T){
    ArmsUtils.obtainAppComponentFromContext(this.getContext())
            .imageLoader().loadImage(this.context, config)
}

fun ImageView.loadImage(url:String){
    loadImage(url,0)
}
fun ImageView.loadImage(url:String,placeholder:Int){
    ArmsUtils.obtainAppComponentFromContext(this.getContext())
            .imageLoader().loadImage(this.context,   ImageConfigImpl.builder()
                    .url(url)
                    .placeholder(placeholder)
                    .imageView(this)
                    .build())
}

