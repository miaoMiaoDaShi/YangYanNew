package com.yangyan.xxp.yangyannew.app

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.jess.arms.http.imageloader.BaseImageLoaderStrategy
import com.jess.arms.http.imageloader.glide.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/6/11
 * Description :
 */
class YangYanGlideImageLoaderStrategy : BaseImageLoaderStrategy<YangYanImageConfig>, GlideAppliesOptions {

    override fun loadImage(ctx: Context?, config: YangYanImageConfig?) {
        if (ctx == null) throw NullPointerException("Context is required")
        if (config == null) throw NullPointerException("ImageConfigImpl is required")
        if (TextUtils.isEmpty(config.url)) throw NullPointerException("Url is required")
        if (config.imageView == null) throw NullPointerException("Imageview is required")


        val requests: GlideRequests

        requests = GlideArms.with(ctx)//如果context是activity则自动使用Activity的生命周期

        val glideRequest = requests.load(config.url)

        when (config.cacheStrategy) {
            //缓存策略
            0 -> glideRequest.diskCacheStrategy(DiskCacheStrategy.ALL)
            1 -> glideRequest.diskCacheStrategy(DiskCacheStrategy.NONE)
            2 -> glideRequest.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            3 -> glideRequest.diskCacheStrategy(DiskCacheStrategy.DATA)
            4 -> glideRequest.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            else -> glideRequest.diskCacheStrategy(DiskCacheStrategy.ALL)
        }

        if (config.isCrossFade) {
            glideRequest.transition(DrawableTransitionOptions.withCrossFade())
        }

        if (config.isCenterCrop) {
            glideRequest.centerCrop()
        }

        if (config.isCircle) {
            glideRequest.circleCrop()
        }

        if (config.transformation != null) {
            glideRequest.transform(config.transformation!!)
        }
        if (config.imageRadius != 0) {
            glideRequest.transform(RoundedCorners(config.imageRadius))
        }



        if (config.resizeX != 0 && config.resizeY != 0) {
            glideRequest.override(config.resizeX, config.resizeY)
        }



        if (config.placeholder != 0)
        //设置占位符
            glideRequest.placeholder(config.placeholder)

        if (config.errorPic != 0)
        //设置错误的图片
            glideRequest.error(config.errorPic)

        if (config.fallback != 0)
        //设置请求 url 为空图片
            glideRequest.fallback(config.fallback)


        glideRequest
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        e?.printStackTrace()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        return false
                    }
                })
                .into(config.imageView)
    }

    override fun clear(ctx: Context?, config: YangYanImageConfig?) {
        if (ctx == null) throw NullPointerException("Context is required")
        if (config == null) throw NullPointerException("ImageConfigImpl is required")
        config.imageViews?.let {
            for (imageView in it) {
                GlideArms.get(ctx).requestManagerRetriever.get(ctx).clear(imageView)
            }
        }

        if (config.isClearDiskCache) {//清除本地缓存
            Observable.just(0)
                    .observeOn(Schedulers.io())
                    .subscribe { Glide.get(ctx).clearDiskCache() }
        }

        if (config.isClearMemory) {//清除内存缓存
            Observable.just(0)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { Glide.get(ctx).clearMemory() }
        }

    }


    override fun applyGlideOptions(context: Context, builder: GlideBuilder) {
        Timber.w("applyGlideOptions")
    }
}
