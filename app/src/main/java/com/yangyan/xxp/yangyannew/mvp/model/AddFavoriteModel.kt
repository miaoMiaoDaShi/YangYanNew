package com.yangyan.xxp.yangyannew.mvp.model

import cn.bmob.v3.BmobUser
import cn.bmob.v3.datatype.BmobFile
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener
import cn.bmob.v3.listener.UploadFileListener
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yangyan.xxp.yangyannew.mvp.contract.AddFavoriteContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.FavoriteInfo
import com.yangyan.xxp.yangyannew.mvp.model.entity.UserInfo
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import java.io.File
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/28
 * Description :添加收藏夹
 */
@ActivityScope
class AddFavoriteModel @Inject
constructor(repositoryManager: IRepositoryManager) : FavoriteModel(repositoryManager)
        , AddFavoriteContract.Model {


}