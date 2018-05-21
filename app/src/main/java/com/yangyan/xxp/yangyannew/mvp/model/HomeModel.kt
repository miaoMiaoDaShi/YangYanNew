package com.yangyan.xxp.yangyannew.mvp.model

import com.yangyan.xxp.yangyannew.mvp.contract.HomeContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import io.reactivex.Observable

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description :
 */
class HomeModel : HomeContract.Model {
    override fun getHomeData(pageIndex: Int): Observable<List<ImagesInfo>> {

        return Observable.just(listOf<ImagesInfo>())
    }

    override fun onDestroy() {
    }
}