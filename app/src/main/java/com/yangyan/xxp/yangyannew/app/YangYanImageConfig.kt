package com.yangyan.xxp.yangyannew.app

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.jess.arms.http.imageloader.ImageConfig

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/6/11
 * Description :
 */
class YangYanImageConfig private constructor(builder: Builder) : ImageConfig() {
    var cacheStrategy: Int = 0//0对应DiskCacheStrategy.all,1对应DiskCacheStrategy.NONE,2对应DiskCacheStrategy.SOURCE,3对应DiskCacheStrategy.RESULT
    var fallback: Int = 0 //请求 url 为空,则使用此图片作为占位符
    var imageRadius: Int = 0//图片每个圆角的大小
    var blurValue: Int = 0//高斯模糊值, 值越大模糊效果越大

    var imageViews: Array<ImageView>? = null
    var isCrossFade: Boolean = false//是否使用淡入淡出过渡动画
    var isCenterCrop: Boolean = false//是否将图片剪切为 CenterCrop
    var isCircle: Boolean = false//是否将图片剪切为圆形
    var isClearMemory: Boolean = false//清理内存缓存
    var isClearDiskCache: Boolean = false//清理本地缓存]

    var resizeX = 0
    var resizeY = 0
    var transformation: Transformation<Bitmap>? = null

    class Builder {
        var url: String? = null
        var imageView: ImageView? = null
        var placeholder: Int = 0
        var errorPic: Int = 0
        var fallback: Int = 0 //请求 url 为空,则使用此图片作为占位符
        var cacheStrategy: Int = 0//0对应DiskCacheStrategy.all,1对应DiskCacheStrategy.NONE,2对应DiskCacheStrategy.SOURCE,3对应DiskCacheStrategy.RESULT
        var imageRadius: Int = 0//图片每个圆角的大小
        var blurValue: Int = 0//高斯模糊值, 值越大模糊效果越大

        var imageViews: Array<ImageView>? = null
        var isCrossFade: Boolean = false//是否使用淡入淡出过渡动画
        var isCenterCrop: Boolean = false//是否将图片剪切为 CenterCrop
        var isCircle: Boolean = false//是否将图片剪切为圆形
        var isClearMemory: Boolean = false//清理内存缓存
        var isClearDiskCache: Boolean = false//清理本地缓存
        var resizeX = 0
        var resizeY = 0
        var transformation: Transformation<Bitmap>? = null

        fun url(url: String): Builder {
            this.url = url
            return this
        }

        fun placeholder(placeholder: Int): Builder {
            this.placeholder = placeholder
            return this
        }

        fun errorPic(errorPic: Int): Builder {
            this.errorPic = errorPic
            return this
        }

        fun fallback(fallback: Int): Builder {
            this.fallback = fallback
            return this
        }

        fun imageView(imageView: ImageView): Builder {
            this.imageView = imageView
            return this
        }

        fun cacheStrategy(cacheStrategy: Int): Builder {
            this.cacheStrategy = cacheStrategy
            return this
        }

        fun imageRadius(imageRadius: Int): Builder {
            this.imageRadius = imageRadius
            return this
        }

        fun blurValue(blurValue: Int): Builder { //blurValue 建议设置为 15
            this.blurValue = blurValue
            return this
        }

        fun imageViews(imageViews: Array<ImageView>?): Builder {
            this.imageViews = imageViews
            return this
        }

        fun isCrossFade(isCrossFade: Boolean): Builder {
            this.isCrossFade = isCrossFade
            return this
        }

        fun isCenterCrop(isCenterCrop: Boolean): Builder {
            this.isCenterCrop = isCenterCrop
            return this
        }

        fun isCircle(isCircle: Boolean): Builder {
            this.isCircle = isCircle
            return this
        }

        fun isClearMemory(isClearMemory: Boolean): Builder {
            this.isClearMemory = isClearMemory
            return this
        }

        fun isClearDiskCache(isClearDiskCache: Boolean): Builder {
            this.isClearDiskCache = isClearDiskCache
            return this
        }

        fun bitmapTransformation(transformation: Transformation<Bitmap>): Builder {
            this.transformation = transformation
            return this
        }

        fun resize(resizeX: Int, resizeY: Int): Builder {
            this.resizeX = resizeX
            this.resizeY = resizeY
            return this
        }

        fun build(): YangYanImageConfig {
            return YangYanImageConfig(this)
        }


    }

    init {
        this.url = builder.url
        this.imageView = builder.imageView
        this.placeholder = builder.placeholder
        this.errorPic = builder.errorPic
        this.fallback = builder.fallback
        this.cacheStrategy = builder.cacheStrategy
        this.imageRadius = builder.imageRadius
        this.blurValue = builder.blurValue
        this.imageViews = builder.imageViews
        this.isCrossFade = builder.isCrossFade
        this.isCenterCrop = builder.isCenterCrop
        this.isCircle = builder.isCircle
        this.isClearMemory = builder.isClearMemory
        this.isClearDiskCache = builder.isClearDiskCache
        this.resizeX = builder.resizeX
        this.resizeY = builder.resizeY
        this.transformation = builder.transformation
    }
}