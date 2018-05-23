package com.yangyan.xxp.yangyannew.mvp.model

import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yangyan.xxp.yangyannew.mvp.contract.CategoryContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.CategoryInfo
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description :分类
 */
@FragmentScope
class CategoryModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager),CategoryContract.Model {


    override fun onDestroy() {
    }
}