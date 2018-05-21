package com.yangyan.xxp.yangyannew.utils

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/4/26
 * Description : 根据传入的索引获取分类的名字
 */
class CategoryUtils {
    companion object {
        /**
         * key code
         * value name
         */
        val categoryNameMap = mapOf(
                "tag" to "标致美人",
                "xinggan" to "性感美女",
                "shaonv" to "少女萝莉",
                "mrxt" to "美乳香臀",
                "swmt" to "丝袜美腿",
                "xgtx" to "性感特写",
                "oumei" to "欧美女神",
                "rihandongya" to "日韩东亚",
                "collection" to "女神合集"
        )

        /**
         * 根据名称简写获取分类名
         */
        fun getCategoryByCode(code: String): String {
            val categoryName = categoryNameMap[code]
            return if (categoryName.isNullOrEmpty()) "暂无分组" else categoryName!!
        }


    }
}