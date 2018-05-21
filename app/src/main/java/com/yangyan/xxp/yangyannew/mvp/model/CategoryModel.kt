package com.yangyan.xxp.yangyannew.mvp.model

import com.yangyan.xxp.yangyannew.mvp.contract.CategoryContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.CategoryInfo
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import io.reactivex.Observable

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description :分类
 */
class CategoryModel:CategoryContract.Model {
    override fun getCategoryData(): Observable<List<CategoryInfo>> {

        return Observable.just(listOf<CategoryInfo>())
    }

    override fun onDestroy() {
    }
}