package com.yangyan.xxp.yangyannew.mvp.model

import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yangyan.xxp.yangyannew.mvp.contract.LoginContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.UserInfo
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/23
 * Description :
 */
@ActivityScope
class LoginModel  @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager),LoginContract.Model{
    override fun toLogin(user: UserInfo): Observable<UserInfo> {
        return Observable.create(object : ObservableOnSubscribe<UserInfo> {
            override fun subscribe(emitter: ObservableEmitter<UserInfo>) {
                user.login(object : SaveListener<UserInfo>() {
                    override fun done(p0: UserInfo?, p1: BmobException?) {
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
}