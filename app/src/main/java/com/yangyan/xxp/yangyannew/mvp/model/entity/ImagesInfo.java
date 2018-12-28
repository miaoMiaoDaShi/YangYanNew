package com.yangyan.xxp.yangyannew.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/12/28
 * Description :图片封面
 */
public class ImagesInfo extends BmobObject {
    /**
     * id : 57685
     * title : 日系性感女神Kicho美胸翘臀惹火身材令人窒息
     * imageCount : 36
     * thumbSrc : https://i2.meizitu.net/thumbs/2016/01/57685_23k31_236.jpg
     */

    private int id;
    private String title;
    @SerializedName("img_num")
    private int imageCount;
    @SerializedName("thumb_src_min")
    private String thumbSrc = "";
    private BmobRelation favorites;//关联收藏夹
    private UserInfo user;//关联用户

    public BmobRelation getFavorites() {
        return favorites;
    }

    public void setFavorites(BmobRelation favorites) {
        this.favorites = favorites;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageCount() {
        return imageCount;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }

    public String getThumbSrc() {
        return thumbSrc;
    }

    public void setThumbSrc(String thumbSrc) {
        this.thumbSrc = thumbSrc;
    }
}
