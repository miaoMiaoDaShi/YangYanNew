package com.yangyan.xxp.yangyannew.mvp.model.parser

import com.yangyan.xxp.yangyannew.app.Preference

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/11/18
 * Description :
 */
object ParseFactory {
    val mParseTag by Preference<Int>("parse", 0)

    fun getParse(): IParse {
        return when (mParseTag) {
            0 -> ParseXxxiaoMm()
            else -> ParseXxxiaoMm()
        }
    }

}