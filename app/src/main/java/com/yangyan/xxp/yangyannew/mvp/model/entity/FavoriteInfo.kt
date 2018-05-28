package com.yangyan.xxp.yangyannew.mvp.model.entity

import cn.bmob.v3.BmobObject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/28
 * Description : 收藏夹信息
 */
class FavoriteInfo : BmobObject() {
    var user : UserInfo? = null
    var coverUrl: String = ""
    var title: String = ""
}