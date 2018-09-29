package com.yangyan.xxp.yangyannew

import com.yangyan.xxp.yangyannew.utils.AnalysisHTMLUtils
import org.jsoup.Jsoup

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/9/29
 * Description :
 */
internal object  MainTest {
    @JvmStatic
    fun main(args: Array<String>) {
        val document = Jsoup.connect("http://m.xxxiao.com/new").get()
        val toList = AnalysisHTMLUtils.translationHomePageToList(document.html())
        toList.forEach (::println )
    }
}