package com.yangyan.xxp.yangyannew.mvp.model.parser

import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.utils.CategoryUtils

import org.jsoup.Jsoup
import timber.log.Timber

import java.util.ArrayList

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/2
 * Description : 网页数据的解析从HTMLString 到ImageInfo的集合对象,解析器:jsoup
 */

class ParseXxxiaoMm : IParse {
    val HOST = "http://m.xxxiao.com"
    val KEY = "m.xxxiao.com"
    val TAG = javaClass.simpleName
    override fun parseHome(htmlContent: String, args: Array<String>?): List<ImagesInfo> {
        val images = ArrayList<ImagesInfo>()
        try {
            val document = Jsoup.parse(htmlContent)
            val elements = document.select("div#content").select("article")
            for (element in elements) {
                val categoryAll = element.attr("class")
                // val categoryAll = "blog-view post-258016 post type-post status-publish format-standard has-post-thumbnail hentry category-rihandongya category-zhong"
                val categorys = StringBuilder()
                val id = element.select("article").attr("id").split("-")[1]
                for (i in 0 until categoryAll.split("category-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().size) {
                    if (i == 0) {
                        continue
                    }
                    try {
                        val categoryAllSplit = categoryAll.split("category-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[i]
                        val spaceIndex = categoryAllSplit.indexOf(" ")
                        val category = categoryAllSplit.substring(0, if (spaceIndex == -1) categoryAllSplit.length else spaceIndex)
                        categorys.append(CategoryUtils.getCategoryByCode(category))
                                .append(",")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }

                val title = element.select("header.entry-header").select("h2").select("a").text()
                val link = element.select("header.entry-header").select("h2").select("a").attr("href")
                val imgUrl = "${element.select("img").attr("data-src").substring(0, element.select("img").attr("data-src").lastIndexOf("-"))}.jpg"


                val image = ImagesInfo(
                        id,
                        title,
                        "",
                        HOST + imgUrl.split(KEY)[1],
                        categorys.toString().substring(0, categorys.length - 1),
                        0, 0
                )
                images.add(image)
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }

        return images

    }

    override fun parseCategory(htmlContent: String, args: Array<String>?): List<ImagesInfo> {
        return parseHome(htmlContent, null)
    }

    override fun parseSearch(htmlContent: String, args: Array<String>?): List<ImagesInfo> {
        val images = ArrayList<ImagesInfo>()
        try {
            val document = Jsoup.parse(htmlContent)
            val elements = document.select("div#content").select("article")
            for (element in elements) {
                val categoryAll = element.attr("class")
                val categorys = StringBuilder()
                val id = element.select("article").attr("id").split("-")[1]
                for (i in 0 until categoryAll.split("category-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().size) {
                    if (i == 0) {
                        continue
                    }
                    try {
                        val categoryAllSplit = categoryAll.split("category-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[i]
                        val spaceIndex = categoryAllSplit.indexOf(" ")
                        val category = categoryAllSplit.substring(0, if (spaceIndex == -1) categoryAllSplit.length else spaceIndex)
                        categorys.append(CategoryUtils.getCategoryByCode(category))
                                .append(",")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
                val title = element.select("header.entry-header").select("h2").select("a").text()
                val link = element.select("header.entry-header").select("h2").select("a").attr("href")
                val imgUrl = "${element.select("img").attr("data-src").substring(0, element.select("img").attr("data-src").lastIndexOf("-"))}.jpg"

                val image = ImagesInfo(
                        id,
                        title,
                        "",
                        HOST + imgUrl.split(KEY)[1],
                        categorys.toString().substring(0, categorys.length - 1),
                        0, 0
                )
                images.add(image)
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }

        return images
    }

    override fun parseImageDetailCollection(htmlContent: String, args: Array<String>?): List<ImagesInfo> {
        val images = ArrayList<ImagesInfo>()
        try {
            val document = Jsoup.parse(htmlContent)
            Timber.i("html: " + document)
            val elements = document.select("div#main").select("div#content").select("article#post-${args?.get(0)}")
                    .select("div.entry-content").select("figure").select("a")
            for (element in elements) {

                val imgUrl = element.attr("href")
                val imgDisplay = element.select("img").attr("src")
                val size = imgDisplay.substring(imgDisplay.length - 11, imgDisplay.length - 4)
                val width = size.split("x")[0].toInt()
                val height = size.split("x")[1].toInt()
                val image = ImagesInfo(
                        "",
                        "",
                        HOST + imgDisplay.split(KEY)[1],
                        HOST + imgUrl.split(KEY)[1],
                        "",
                        width,
                        height
                )
                images.add(image)

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return images
    }


}


