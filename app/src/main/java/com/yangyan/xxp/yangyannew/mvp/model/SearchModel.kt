package com.yangyan.xxp.yangyannew.mvp.model

import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yangyan.xxp.yangyannew.mvp.contract.SearchContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.model.parser.ParseFactory
import com.yangyan.xxp.yangyannew.mvp.model.service.CommonService
import com.yangyan.xxp.yangyannew.mvp.model.parser.ParseXxxiaoMm
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description :
 */
@FragmentScope
class SearchModel @Inject
constructor(repositoryManager: IRepositoryManager)
    : BaseModel(repositoryManager),SearchContract.Model {
    override fun onDestroy() {
    }

    override fun searchAtlasByKeyword(pageIndex:Int,keyword: String):Observable<List<ImagesInfo>> {
        return mRepositoryManager.obtainRetrofitService(CommonService::class.java)
                .searchAtlasByKeyWords(pageIndex,keyword)
                .map { html: String -> ParseFactory.getParse().parseSearch(html,null) }
    }
}