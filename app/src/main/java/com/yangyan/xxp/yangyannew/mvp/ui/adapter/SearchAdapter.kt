package com.yangyan.xxp.yangyannew.mvp.ui.adapter

import android.graphics.Color
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.app.YangYanImageConfig
import com.yangyan.xxp.yangyannew.app.loadImage
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.CropTransformation

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/22
 * Description :  搜索页面的
 */
class SearchAdapter
constructor(val mDatas: MutableList<ImagesInfo>)
    : BaseQuickAdapter<ImagesInfo, BaseViewHolder>(R.layout.recycler_home_image, mDatas) {
    override fun convert(helper: BaseViewHolder, item: ImagesInfo) {
        val spannableString = SpannableString(item.title)
        try {
            val colorSpan = ForegroundColorSpan(Color.parseColor("#44f3e7"))
            val indexStart = item.title.indexOf(mKeyWords)
            val endStart = indexStart + mKeyWords.length
            spannableString.setSpan(colorSpan, indexStart, endStart, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        } catch (e: Exception) {

        }
        helper
                .apply {
                    setText(R.id.mTvCount, "${item.imageCount}P")
                    setText(R.id.mTvTitle, spannableString)
                    getView<ImageView>(R.id.mIvImage).apply {
                        post {
                            loadImage(YangYanImageConfig.Builder()
                                    .bitmapTransformation(BlurTransformation(50))
                                    .imageView(this)
                                    .url(item.thumbSrc)
                                    .placeholder(R.drawable.bg_loading)
                                    .build())
                        }

                    }
                    getView<ImageView>(R.id.mCircleIvImage).apply {
                        post {
                            loadImage(YangYanImageConfig.Builder()
                                    .bitmapTransformation(CropTransformation(this.width, this.height, CropTransformation.CropType.TOP))
                                    .imageView(this)
                                    .url(item.thumbSrc)
                                    .placeholder(R.drawable.bg_loading)
                                    .build())
                        }

                    }
                }


    }

    /**
     * 关键字
     */
    private var mKeyWords = ""


    /**
     * 传入关键字
     */
    fun setKeyWords(keyWords: String) {
        this.mKeyWords = keyWords
    }
}