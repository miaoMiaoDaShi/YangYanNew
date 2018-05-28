package com.yangyan.xxp.yangyannew.app

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zhihu.matisse.engine.ImageEngine

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/3/31
 * Description :
 */
class GlideImageEngine : ImageEngine {
    override fun loadAnimatedGifThumbnail(context: Context, resize: Int, placeholder: Drawable?, imageView: ImageView, uri: Uri?) {
        Glide.with(context)
                .asGif()
                .load(uri)
                .apply(RequestOptions().centerCrop().placeholder(placeholder).override(resize,resize))
                .into(imageView)
    }

    override fun loadImage(context: Context, resizeX: Int, resizeY: Int, imageView: ImageView, uri: Uri?) {
        Glide.with(context)
                .load(uri)
                .apply(RequestOptions().centerCrop().override(resizeX,resizeY))
                .into(imageView)
    }

    override fun loadAnimatedGifImage(context: Context, resizeX: Int, resizeY: Int, imageView: ImageView, uri: Uri?) {
        Glide.with(context)
                .asBitmap()
                .apply(RequestOptions().centerCrop().override(resizeX,resizeY))
                .load(uri)
                .into(imageView)
    }

    override fun supportAnimatedGif(): Boolean  = false

    override fun loadThumbnail(context: Context, resize: Int, placeholder: Drawable?, imageView: ImageView, uri: Uri?) {
        Glide.with(context)
                .load(uri)
                .apply(RequestOptions().centerCrop().placeholder(placeholder).override(resize,resize))
                .into(imageView)
    }
}