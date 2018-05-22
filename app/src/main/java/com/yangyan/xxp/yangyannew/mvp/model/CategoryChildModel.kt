package com.yangyan.xxp.yangyannew.mvp.model

import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yangyan.xxp.yangyannew.mvp.contract.CategoryChildContract
import com.yangyan.xxp.yangyannew.mvp.contract.CategoryContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.model.service.CommonService
import com.yangyan.xxp.yangyannew.utils.AnalysisHTMLUtils
import io.reactivex.Observable
import okhttp3.ResponseBody
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/22
 * Description :  分类子页面
 */
class CategoryChildModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), CategoryChildContract.Model{
    override fun getCategoryChildData(categoryCode:String,pageIndex: Int): Observable<List<ImagesInfo>> {
        return mRepositoryManager.obtainRetrofitService(CommonService::class.java)
                .getAtlasListByCategory(categoryCode,pageIndex)
                .map { html: ResponseBody -> AnalysisHTMLUtils.HomePageToList(html.string()) }
    }

    override fun onDestroy() {
    }

}