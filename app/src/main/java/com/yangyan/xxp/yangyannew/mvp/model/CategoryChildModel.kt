package com.yangyan.xxp.yangyannew.mvp.model

import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yangyan.xxp.yangyannew.mvp.contract.CategoryChildContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.model.parser.IParse
import com.yangyan.xxp.yangyannew.mvp.model.parser.ParseFactory
import com.yangyan.xxp.yangyannew.mvp.model.service.CommonService
import com.yangyan.xxp.yangyannew.mvp.model.service.cache.CommonCacheService
import com.yangyan.xxp.yangyannew.mvp.model.parser.ParseXxxiaoMm
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.DynamicKeyGroup
import io.rx_cache2.Reply
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/22
 * Description :  分类子页面
 */
@FragmentScope
class CategoryChildModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), CategoryChildContract.Model {



    override fun getCategoryChildData(categoryCode: String, pageIndex: Int): Observable<List<ImagesInfo>> {
        return mRepositoryManager.obtainCacheService(CommonCacheService::class.java)
                    .getAtlasListByCategory(mRepositoryManager.obtainRetrofitService(CommonService::class.java)
                            .getAtlasListByCategory(categoryCode, pageIndex),
                            DynamicKeyGroup(categoryCode, pageIndex))
                    .map { html: Reply<String> -> ParseFactory.getParse().parseCategory(html.data,null) }



    }

    override fun onDestroy() {
    }

}