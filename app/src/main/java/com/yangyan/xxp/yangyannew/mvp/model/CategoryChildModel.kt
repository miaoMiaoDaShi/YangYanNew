package com.yangyan.xxp.yangyannew.mvp.model

import com.google.gson.Gson
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yangyan.xxp.yangyannew.app.Preference
import com.yangyan.xxp.yangyannew.mvp.contract.CategoryChildContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.model.entity.UserInfo
import com.yangyan.xxp.yangyannew.mvp.model.service.CommonService
import com.yangyan.xxp.yangyannew.mvp.model.service.cache.CommonCacheService
import com.yangyan.xxp.yangyannew.utils.AnalysisHTMLUtils
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.DynamicKeyGroup
import io.rx_cache2.Reply
import okhttp3.ResponseBody
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
        return if ("tag" == categoryCode) {
            mRepositoryManager.obtainCacheService(CommonCacheService::class.java)
                    .getTagAtlasList(mRepositoryManager.obtainRetrofitService(CommonService::class.java)
                            .getTagAtlasList(pageIndex),
                            DynamicKey(pageIndex))

                    .map { html: Reply<String> -> AnalysisHTMLUtils.translationTagPageToList(html.data) }
        } else {
            mRepositoryManager.obtainCacheService(CommonCacheService::class.java)
                    .getAtlasListByCategory(mRepositoryManager.obtainRetrofitService(CommonService::class.java)
                            .getAtlasListByCategory(categoryCode, pageIndex),
                            DynamicKeyGroup(categoryCode, pageIndex))
                    .map { html: Reply<String> -> AnalysisHTMLUtils.translationHomePageToList(html.data) }
        }

    }

    override fun onDestroy() {
    }

}