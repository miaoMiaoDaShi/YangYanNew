package com.yangyan.xxp.yangyannew.mvp.model

import com.google.gson.Gson
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yangyan.xxp.yangyannew.app.Preference
import com.yangyan.xxp.yangyannew.mvp.contract.ImageCollectionContract
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
 * Time :  2018/5/25
 * Description :  图片集合
 */
@ActivityScope
class ImageCollectionModel @Inject
constructor(repositoryManager: IRepositoryManager) : FavoriteModel(repositoryManager), ImageCollectionContract.Model {
    override fun getIamgeCollection(id: String): Observable<List<ImagesInfo>> {
        return mRepositoryManager.obtainCacheService(CommonCacheService::class.java)
                .getAtlasDetailById(mRepositoryManager.obtainRetrofitService(CommonService::class.java)
                        .getAtlasDetailById(id),
                        DynamicKey(id))
                .map { html: Reply<String> ->
                    AnalysisHTMLUtils.translationParticularsToList(html.data, id)
                }
    }

}