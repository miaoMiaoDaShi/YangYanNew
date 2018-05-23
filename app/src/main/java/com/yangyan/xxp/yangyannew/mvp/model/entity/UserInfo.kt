package com.yangyan.xxp.yangyannew.mvp.model.entity

import cn.bmob.v3.BmobUser

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/23
 * Description :用户信息
 */
data class UserInfo(
        val id: String,//用户ID
        val userPortrait: String//头像
):BmobUser()