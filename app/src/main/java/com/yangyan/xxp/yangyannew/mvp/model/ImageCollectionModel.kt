package com.yangyan.xxp.yangyannew.mvp.model

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yangyan.xxp.yangyannew.mvp.contract.ImageCollectionContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.model.service.CommonService
import com.yangyan.xxp.yangyannew.utils.AnalysisHTMLUtils
import io.reactivex.Observable
import okhttp3.ResponseBody
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/25
 * Description :  图片集合
 */
@ActivityScope
class ImageCollectionModel@Inject
constructor(repositoryManager: IRepositoryManager) : FavoriteModel(repositoryManager), ImageCollectionContract.Model{
    override fun getIamgeCollection(id:String): Observable<List<ImagesInfo>> {
        return mRepositoryManager.obtainRetrofitService(CommonService::class.java)
                .getAtlasDetailById(id)
                .map { html: ResponseBody ->
                    AnalysisHTMLUtils.translationParticularsToList(html.string(),id) }
    }

}