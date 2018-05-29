package com.yangyan.xxp.yangyannew.mvp.model.entity

import cn.bmob.v3.BmobObject
import cn.bmob.v3.datatype.BmobRelation

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description : 图片信息 (通用)
 */
data class ImagesInfo(
        val id: String,//id
        val title: String,//显示标题
        val displayImageUrl: String,//缩略图
        val HDImageUrl: String,//高清图片地址
        val category: String,//分类
        val width: Int, //宽度
        var height: Int//高度
):BmobObject (){
    var favorites: BmobRelation? = null//关联收藏夹
    var user: UserInfo? = null//关联用户
}