package com.yangyan.xxp.yangyannew.mvp.model.entity

import cn.bmob.v3.BmobObject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/28
 * Description : 收藏夹信息
 */
class FavoriteInfo : BmobObject(), Cloneable {
    var user: UserInfo? = null
    var coverUrl: String = ""
    var title: String = ""
    var isDefault: Boolean = false
    var isChecked: Boolean = false//选中状态???数据库中无意义
    override fun toString(): String {
        return "FavoriteInfo(user=$user, coverUrl='$coverUrl', title='$title', isDefault=$isDefault, isChecked=$isChecked)"
    }

    public override fun clone(): FavoriteInfo {
        return super.clone() as FavoriteInfo
    }


}