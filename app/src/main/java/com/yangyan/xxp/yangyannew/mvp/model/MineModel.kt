package com.yangyan.xxp.yangyannew.mvp.model

import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yangyan.xxp.yangyannew.mvp.contract.MineContract
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description :个人页面
 */
@FragmentScope
class MineModel
@Inject
constructor(repositoryManager: IRepositoryManager)
    : BaseModel(repositoryManager), MineContract.Model {
    override fun loadMineData() {


    }

    override fun loadCollectDataTag() {
    }

    override fun loadCollectDataDetailByTag(categoryIndex: Int) {
    }

    override fun onDestroy() {
    }
}