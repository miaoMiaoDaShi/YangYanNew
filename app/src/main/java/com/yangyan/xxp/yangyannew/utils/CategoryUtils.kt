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
                "tag" to "t",
                "xinggan" to "x",
                "shaonv" to "s",
                "mrxt" to "m",
                "swmt" to "s",
                "xgtx" to "f",
                "oumei" to "g",
                "rihandongya" to "h",
                "collection" to "i"
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