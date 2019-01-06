package com.yangyan.xxp.yangyannew.mvp.model

import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.UpdateListener
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.integration.IRepositoryManager
import com.yangyan.xxp.yangyannew.mvp.contract.MineContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.FavoriteInfo
import com.yangyan.xxp.yangyannew.mvp.model.entity.MineZipInfo
import com.yangyan.xxp.yangyannew.mvp.model.entity.UserInfo
import io.reactivex.*
import io.reactivex.functions.BiFunction
import timber.log.Timber
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description :个人页面
 */
@FragmentScope
class MineModel
@Inject
constructor(repositoryManager: IRepositoryManager)
    : FavoriteModel(repositoryManager), MineContract.Model {
    private val mAddFavoriteModel by lazy {
        AddFavoriteModel(repositoryManager)
    }

    override fun loadMineData(userInfo: UserInfo): Observable<MineZipInfo> {
        return loadUserInfo(userInfo)
                //整合userInfo\ 和 收藏夹列表 到 MineZipInfo
                //为什么不用zipWith????这里时间先后很重要
                .flatMap { userInfoNew -> getFavorite().map { favorites -> MineZipInfo(userInfoNew, favorites) } }

    }

    override fun loadUserInfo(userInfo: UserInfo): Observable<UserInfo> {
        return Observable.create(object : ObservableOnSubscribe<UserInfo> {
            override fun subscribe(emitter: ObservableEmitter<UserInfo>) {
                val bmobQuery = BmobQuery<UserInfo>()
                bmobQuery.addWhereEqualTo("username", userInfo.username)
                bmobQuery.findObjects(object : FindListener<UserInfo>() {
                    override fun done(p0: MutableList<UserInfo>?, p1: BmobException?) {
                        p0?.let {
                            emitter.onNext(it[0])
                            emitter.onComplete()
                            return
                        }
                        p1?.let {
                            emitter.onError(it)
                        }
                    }
                })
            }
        })
                .flatMap { userInfo ->
                    //如果默认分组id为空,则说明没有默认分组
                    if (userInfo.defaultFavoriteId.isEmpty()) {
                        //添加默认分组
                        mAddFavoriteModel
                                .addFavorite(FavoriteInfo()
                                        .apply {
                                            isDefault = true
                                            title = "删除收藏夹后的图片都在这里哦"
                                            coverUrl = "https://yang-yan-new-1252246683.cos.ap-chengdu.myqcloud.com/bg_yang_yan_new_default_favorite.png"
                                        })
                                //添加成功后得到默认分组的id
                                //更新用户信息
                                .flatMap { objectId ->
                                    updateUserInfo(BmobUser.getCurrentUser(UserInfo::class.java).apply {
                                        defaultFavoriteId = objectId
                                    })
                                            .map { objectId }
                                            //从新获取用户信息
                                            .flatMap {
                                                Observable.just(userInfo.apply { defaultFavoriteId = objectId })
                                            }
                                }
                    } else {
                        Observable.just(userInfo)
                    }
                }
    }

    override fun updateUserInfo(userInfo: UserInfo): Observable<Int> {
        return Observable.create(object : ObservableOnSubscribe<Int> {
            override fun subscribe(emitter: ObservableEmitter<Int>) {
                userInfo.update(object : UpdateListener() {
                    override fun done(p0: BmobException?) {
                        p0?.let {
                            emitter.onError(it)
                            return
                        }
                        emitter.onNext(200)
                        emitter.onComplete()
                    }

                })
            }
        })

    }


    override fun onDestroy() {
    }
}