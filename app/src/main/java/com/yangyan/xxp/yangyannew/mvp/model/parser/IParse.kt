package com.yangyan.xxp.yangyannew.mvp.model.parser

import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/11/18
 * Description :
 */
interface IParse {
    /**
     * 转换数据的到主页的
     */
    fun parseHome(htmlContent: String,args:Array<String>?): List<ImagesInfo>

    /**
     * 转换数据的到分类的
     */
    fun parseCategory(htmlContent: String,args:Array<String>?): List<ImagesInfo>

    /**
     * 搜索使用的
     */
    fun parseSearch(htmlContent: String,args:Array<String>?): List<ImagesInfo>

    /**
     * 得到详细的图片中的图片集合 可能会有参数
     */
    fun parseImageDetailCollection(htmlContent:String,args:Array<String>?): List<ImagesInfo>
}