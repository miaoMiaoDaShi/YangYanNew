package com.yangyan.xxp.yangyannew.mvp.model

import com.google.gson.Gson
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yangyan.xxp.yangyannew.app.Preference
import com.yangyan.xxp.yangyannew.mvp.contract.HomeContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.model.entity.UserInfo
import com.yangyan.xxp.yangyannew.mvp.model.service.CommonService
import com.yangyan.xxp.yangyannew.mvp.model.service.cache.CommonCacheService
import com.yangyan.xxp.yangyannew.utils.AnalysisHTMLUtils
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.DynamicKeyGroup
import io.rx_cache2.Reply
import io.rx_cache2.Source
import timber.log.Timber
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description :
 */
@FragmentScope
class HomeModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), HomeContract.Model {
    override fun getHomeData(pageIndex: Int): Observable<List<ImagesInfo>> {
        return mRepositoryManager.obtainCacheService(CommonCacheService::class.java)
                .getNewAtlasList(mRepositoryManager.obtainRetrofitService(CommonService::class.java)
                        .getNewAtlasList(pageIndex),
                        DynamicKey(pageIndex))
                .map { reply: Reply<String> ->
                    reply.data.apply {
                        when (reply.source) {
                            Source.CLOUD -> {
                                Timber.i("數據來自網諾")
                            }
                            Source.MEMORY -> {
                                Timber.i("數據來自內存")
                            }
                            Source.PERSISTENCE -> {
                                Timber.i("數據來自磁盤")
                            }
                            else -> {
                            }
                        }
                    }
                }
                .map { html: String -> AnalysisHTMLUtils.translationHomePageToList(html) }
    }


    override fun onDestroy() {
    }
}