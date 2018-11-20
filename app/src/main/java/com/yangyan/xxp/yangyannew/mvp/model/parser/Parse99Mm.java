package com.yangyan.xxp.yangyannew.mvp.model.parser;

import android.text.TextUtils;

import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo;
import com.yangyan.xxp.yangyannew.utils.StringUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;

/**
 * @author flymegoc
 * @date 2018/2/1
 */

public class Parse99Mm {

    private static final String TAG = Parse99Mm.class.getSimpleName();

    public static List<ImagesInfo> parse99MmList(String html, int page) {
        Document doc = Jsoup.parse(html);
        Element ul = doc.getElementById("piclist");
        Elements lis = ul.select("li");
        List<ImagesInfo> mm99List = new ArrayList<>();
        for (Element li : lis) {

            Element a = li.selectFirst("dt").selectFirst("a");
            final String contentUrl = "http://www.99mm.me" + a.attr("href");

            int startIndex = contentUrl.lastIndexOf("/");
            int endIndex = contentUrl.lastIndexOf(".");
            String idStr = StringUtils.subString(contentUrl, startIndex + 1, endIndex);
            Element img = a.selectFirst("img");
            String title = img.attr("alt");

            String imgUrl = img.attr("src");
            HttpUrl httpUrl = HttpUrl.parse(imgUrl);
            if (httpUrl == null) {
                imgUrl = img.attr("data-img");
            }
            int imgWidth = Integer.parseInt(img.attr("width"));

            ImagesInfo mm99 = new ImagesInfo(idStr, title, imgUrl, contentUrl, "未知",0,0);
            mm99List.add(mm99);
        }

        return mm99List;
    }

    public static List<String> parse99MmImageList(String html) {

        Document doc = Jsoup.parse(html);

        Element elementBox = doc.getElementById("picbox");
        String imgUrl = elementBox.selectFirst("img").attr("src").trim();
        HttpUrl httpUrl = HttpUrl.parse(imgUrl);
        Element element = doc.body().select("script").first();
        String javaScript = element.toString();
        String data = StringUtils.subString(javaScript, javaScript.indexOf("'") + 1, javaScript.lastIndexOf(";") - 1);
        String[] ids = data.split(",");
        if (ids.length == 0) {
            ids = data.split("%");
        }

        String[] jmImgUrl = {"img", "file"};

        List<String> stringImageList = new ArrayList<>();
        String jmPicUrl;
        for (int i = 8; i < ids.length; i++) {
            if (httpUrl == null) {
                jmPicUrl = "http://" + jmImgUrl[Integer.parseInt(ids[1])] + ".99mm.net/" + ids[4] + "/" + ids[5] + "/" + (i - 7) + "-" + ids[i].toLowerCase() + ".jpg";
            } else if (!ids[5].equals("\"n\"")) {
                jmPicUrl = "http://" + httpUrl.host() + "/" + ids[4] + "/" + ids[5] + "/" + (i - 7) + "-" + ids[i].toLowerCase() + ".jpg";
            } else {
                jmPicUrl = "http://" + httpUrl.host() + "/" + ids[1] + (i - 7) + "-" + ids[i - 2].toLowerCase() + ".jpg";
            }

            stringImageList.add(jmPicUrl.replace("\"", ""));
        }
        return stringImageList;

    }
}
