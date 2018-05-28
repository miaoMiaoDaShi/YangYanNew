package com.yangyan.xxp.yangyannew.mvp.model

import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yangyan.xxp.yangyannew.mvp.contract.MineContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.FavoriteInfo
import com.yangyan.xxp.yangyannew.mvp.model.entity.UserInfo
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
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
    : BaseModel(repositoryManager), MineContract.Model {
    override fun loadMineData(userInfo: UserInfo):Observable<UserInfo> {
        return Observable.create(object : ObservableOnSubscribe<UserInfo> {
            override fun subscribe(emitter: ObservableEmitter<UserInfo>) {
                val bmobQuery = BmobQuery<UserInfo>()
                bmobQuery.addWhereEqualTo("username",userInfo.username)
                bmobQuery.findObjects(object :FindListener<UserInfo>(){
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

    }
    override fun getFavorite():Observable<List<FavoriteInfo>> {
        return Observable.create(object : ObservableOnSubscribe<List<FavoriteInfo>> {
            override fun subscribe(emitter: ObservableEmitter<List<FavoriteInfo>>) {
                val bmobQuery = BmobQuery<FavoriteInfo>()
                val userInfo = BmobUser.getCurrentUser(UserInfo::class.java)
                bmobQuery.addWhereEqualTo("user",userInfo)
                bmobQuery.findObjects(object :FindListener<FavoriteInfo>(){
                    override fun done(p0: MutableList<FavoriteInfo>?, p1: BmobException?) {
                        p0?.let {
                            emitter.onNext(it)
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

    }



    override fun onDestroy() {
    }
}