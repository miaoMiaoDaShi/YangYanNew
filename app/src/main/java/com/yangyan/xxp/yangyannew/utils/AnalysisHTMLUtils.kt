package com.yangyan.xxp.yangyannew.utils

import android.util.Log
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo

import org.jsoup.Jsoup

import java.util.ArrayList

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/2
 * Description : 网页数据的解析从HTMLString 到ImageInfo的集合对象,解析器:jsoup
 */

object AnalysisHTMLUtils {
    val TAG = javaClass.simpleName
    //获取主页的指定内容
    fun translationHomePageToList(content: String): List<ImagesInfo> {
        val images = ArrayList<ImagesInfo>()
        try {
            val document = Jsoup.parse(content)
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
                val imgUrl = "${element.select("img").attr("data-src").substring(0,element.select("img").attr("data-src").lastIndexOf("-"))}.jpg"


                val image = ImagesInfo(
                        id,
                        title,
                        "",
                        imgUrl,
                        categorys.toString().substring(0, categorys.length - 1),
                        0,0
                )
                images.add(image)
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }

        return images
    }
    /**
     * 搜索
     */
    fun translationSearchPageToList(content: String): List<ImagesInfo> {
        val images = ArrayList<ImagesInfo>()
        try {
            val document = Jsoup.parse(content)
            val elements = document.select("div.posts-layout").select("article")
            for (element in elements) {
                val categoryAll = element.attr("class")
                val categorys = StringBuilder()
                val id = element.attr("id").substring(5)
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
                val imgUrl = element.select("img").attr("src")

                val image = ImagesInfo(
                        id,
                        title,
                        "",
                        imgUrl,
                        categorys.toString().substring(0, categorys.length - 1),
                        0,0
                )
                images.add(image)
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }

        return images
    }

    fun translationTagPageToList(content: String): List<ImagesInfo> {
        val images = ArrayList<ImagesInfo>()
        try {
            val document = Jsoup.parse(content)
            val elements = document.select("div.lsow-grid-container").select("article")
            for (element in elements) {
                println(element)
                val categoryAll = element.attr("class")
                val categorys = StringBuilder()
                val id = categoryAll.substring(5, categoryAll.indexOf(" "))
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
                val title = element.select("div.lsow-entry-info").select("h3").select("a").attr("title")
                val link = element.select("header.entry-header").select("h2").select("a").attr("href")
                val imgUrl = element.select("img").attr("src")

                val image = ImagesInfo(
                        id,
                        title,
                        "",
                        imgUrl,
                        categorys.toString().substring(0, categorys.length - 1),
                        0,
                        0
                )
                images.add(image)
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }

        return images
    }

    /**
     * 套图装换
     *
     * @param content
     * @param id
     * @return
     */
    fun translationParticularsToList(content: String, id: String): List<ImagesInfo> {
        val images = ArrayList<ImagesInfo>()
        try {
            val document = Jsoup.parse(content)
            val elements = document.select("div#page").select("div#content").select("div.container")
                    .select("div.row").select("div#primary").select("main#main").select("article#post-$id")
                    .select("div.single-content").select("div.rgg-imagegrid").select("a")
            for (element in elements) {
                val imgUrlAll = element.attr("data-src")
                val spaceIndex = imgUrlAll.lastIndexOf("-")

                val imgUrl = imgUrlAll.substring(0, spaceIndex) + ".jpg"
                val imgDisplay = element.attr("data-src")
                //http://m.xxxiao.com/wp-content/uploads/sites/3/2018/05/438ccb1f61b3d73a-200x300.jpg
                val width = element.attr("data-width").toInt()
                val height = element.attr("data-height").toInt()
                val image = ImagesInfo(
                        "",
                        "",
                        imgDisplay,
                        imgUrl,
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
