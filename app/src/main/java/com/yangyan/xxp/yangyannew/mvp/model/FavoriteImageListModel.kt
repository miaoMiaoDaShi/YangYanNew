package com.yangyan.xxp.yangyannew.mvp.model

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yangyan.xxp.yangyannew.mvp.contract.FavoriteImageListContract
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/29
 * Description :  收藏夹点进去的页面
 */

@ActivityScope
class FavoriteImageListModel @Inject
constructor(repositoryManager: IRepositoryManager) : FavoriteModel(repositoryManager), FavoriteImageListContract.Model {
}