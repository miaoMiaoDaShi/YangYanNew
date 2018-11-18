package com.yangyan.xxp.yangyannew.mvp.model

import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yangyan.xxp.yangyannew.mvp.contract.HomeContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.model.entity.SystemMsg
import com.yangyan.xxp.yangyannew.mvp.model.parser.ParseFactory
import com.yangyan.xxp.yangyannew.mvp.model.service.CommonService
import com.yangyan.xxp.yangyannew.mvp.model.service.cache.CommonCacheService
import com.yangyan.xxp.yangyannew.mvp.model.parser.ParseXxxiaoMm
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.rx_cache2.DynamicKey
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
                .map { html: String -> ParseFactory.getParse().parseHome(html,null) }
    }


    /**
     * 获取系统通知消息
     */
    override fun getSystemMsg(): Observable<String> {
        return Observable.create(object : ObservableOnSubscribe<String> {
            override fun subscribe(emitter: ObservableEmitter<String>) {
                val bmobQuery = BmobQuery<SystemMsg>()
                bmobQuery.findObjects(object : FindListener<SystemMsg>() {
                    override fun done(p0: MutableList<SystemMsg>?, p1: BmobException?) {
                        p0?.let {
                            if(!it[0].content.isNullOrEmpty()){
                                emitter.onNext(it[0].content!!)
                                emitter.onComplete()
                                return
                            }


                        }
                        p1?.let {
                            emitter.onError(it)
                        }
                    }
                })
            }
        })
    }


    override fun onDestroy() {
    }
}