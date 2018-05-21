package com.yangyan.xxp.yangyannew.utils;

import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/2
 * Description : 网页数据的解析从HTMLString 到ImageInfo的集合对象,解析器:jsoup
 */

public class AnalysisHTMLUtils {
    //获取主页的指定内容
    public static List<ImagesInfo> HomePageToList(String content) {
        List<ImagesInfo> images = new ArrayList<>();
        try {
//            final Map<String, CategoryInfo> categoryMaps = new HashMap<>();
//            CategoryInfo categoryInfo = new CategoryInfo();
//
//            categoryInfo.setIndex(0);
//            categoryInfo.setName("标致美人");
//            categoryMaps.put("tag", categoryInfo);
//
//            categoryInfo = categoryInfo.clone();
//            categoryInfo.setIndex(1);
//            categoryInfo.setName("性感美女");
//            categoryMaps.put("xinggan", "性感美女");
//
//            categoryInfo.setIndex(1);
//            categoryInfo.setName("少女萝莉");
//            categoryMaps.put("shaonv", "少女萝莉");
//
//            categoryInfo.setIndex(1);
//            categoryInfo.setName("美乳香臀");
//            categoryMaps.put("mrxt", "美乳香臀");
//
//            categoryInfo.setIndex(1);
//            categoryInfo.setName("丝袜美腿");
//            categoryMaps.put("swmt", "丝袜美腿");
//
//            categoryInfo.setIndex(1);
//            categoryInfo.setName("性感特写");
//            categoryMaps.put("xgtx", "性感特写");
//
//            categoryInfo.setIndex(1);
//            categoryInfo.setName("欧美女神");
//            categoryMaps.put("oumei", "欧美女神");
//
//            categoryInfo.setIndex(1);
//            categoryInfo.setName("日韩东亚");
//            categoryMaps.put("rihandongya", "日韩东亚");
//
//            categoryInfo.setIndex(1);
//            categoryInfo.setName("女神合集");
//            categoryMaps.put("collection", "女神合集");

            final Document document = Jsoup.parse(content);
            final Elements elements = document.select("div.posts-layout").select("article");
            for (Element element : elements) {
                final String categoryAll = element.attr("class");
                final StringBuilder categorys = new StringBuilder();
                final String id = categoryAll.substring(5, categoryAll.indexOf(" "));
                for (int i = 0; i < categoryAll.split("category-").length; i++) {
                    if (i == 0) {
                        continue;
                    }
                    try {
                        final String categoryAllSplit = categoryAll.split("category-")[i];
                        final int spaceIndex = categoryAllSplit.indexOf(" ");
                        final String category = categoryAllSplit.substring(0, spaceIndex == -1 ? categoryAllSplit.length() : spaceIndex);
                        categorys.append(CategoryUtils.Companion.getCategoryByCode(category))
                                .append(",");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                final String title = element.select("header.entry-header").select("h2").select("a").text();
                final String link = element.select("header.entry-header").select("h2").select("a").attr("href");
                final String imgUrl = element.select("img").attr("src");

                final ImagesInfo image = new ImagesInfo(
                        id,
                        title,
                        "",
                        imgUrl,
                        categorys.toString().substring(0, categorys.length() - 1)
                        );
                images.add(image);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
    }

    /**
     * 套图装换
     *
     * @param content
     * @param id
     * @return
     */
    public static List<ImagesInfo> ParticularsToList(String content, String id) {
        List<ImagesInfo> images = new ArrayList<>();
        try {
            Document document = Jsoup.parse(content);
            Elements elements = document.select("div#page").select("div#content").select("div.container")
                    .select("div.row").select("div#primary").select("main#main").select("article#post-" + id)
                    .select("div.single-content").select("div.rgg-imagegrid").select("a");
            for (Element element : elements) {
                final String imgUrlAll = element.attr("data-src");
                final int spaceIndex = imgUrlAll.lastIndexOf("-");

                String imgUrl = imgUrlAll.substring(0, spaceIndex) + ".jpg";
                String imgDisplay = element.attr("data-src");
                ImagesInfo image = new ImagesInfo(
                        "",
                        "",
                        imgDisplay,
                        imgUrl,
                        ""
                        );
                images.add(image);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
    }
}
