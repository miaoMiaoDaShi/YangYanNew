package com.yangyan.xxp.yangyannew.mvp.model

import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yangyan.xxp.yangyannew.mvp.contract.HomeContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.utils.AnalysisHTMLUtils
import io.reactivex.Observable
import okhttp3.ResponseBody

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description :
 */
class HomeModel(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), HomeContract.Model {
    override fun getHomeData(pageIndex: Int): Observable<List<ImagesInfo>> {
        return mRepositoryManager.obtainRetrofitService(CommonService::class.java)
                .getNewAtlasList(pageIndex)
                .map { html: ResponseBody -> AnalysisHTMLUtils.HomePageToList(html.string()) }
    }

    override fun onDestroy() {
    }
}