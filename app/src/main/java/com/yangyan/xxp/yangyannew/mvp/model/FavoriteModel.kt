package com.yangyan.xxp.yangyannew.mvp.model

import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.datatype.BmobFile
import cn.bmob.v3.datatype.BmobPointer
import cn.bmob.v3.datatype.BmobRelation
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UploadFileListener
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.tencent.cos.xml.CosXmlSimpleService
import com.tencent.cos.xml.exception.CosXmlClientException
import com.tencent.cos.xml.exception.CosXmlServiceException
import com.tencent.cos.xml.listener.CosXmlResultListener
import com.tencent.cos.xml.model.CosXmlRequest
import com.tencent.cos.xml.model.CosXmlResult
import com.tencent.cos.xml.transfer.TransferConfig
import com.tencent.cos.xml.transfer.TransferManager
import com.yangyan.xxp.yangyannew.mvp.contract.FavoriteContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.FavoriteInfo
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.model.entity.UserInfo
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import timber.log.Timber
import java.io.File
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/29
 * Description : 收藏相关类---强行继承使用
 */
abstract class FavoriteModel
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), FavoriteContract.Model {
    @Inject
    lateinit var mCosXmlSimpleService: CosXmlSimpleService
    /**
     * 添加收藏夹
     */
    override fun addFavorite(favoriteInfo: FavoriteInfo): Observable<String> {
        return Observable.create(object : ObservableOnSubscribe<String> {
            override fun subscribe(emitter: ObservableEmitter<String>) {
                val userInfo = BmobUser.getCurrentUser(UserInfo::class.java)
                favoriteInfo.user = userInfo
                favoriteInfo.save(object : SaveListener<String>() {
                    override fun done(p0: String?, p1: BmobException?) {
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

    /**
     * 上传封面
     */
    override fun uploadCover(imagePath: String): Observable<String> {
        return Observable.create(object : ObservableOnSubscribe<String> {
            override fun subscribe(emitter: ObservableEmitter<String>) {
                TransferManager(mCosXmlSimpleService,TransferConfig.Builder().build())
                        .upload("yang-yan-new-1252246683",
                                "${System.currentTimeMillis()}-${File(imagePath).name}",
                                imagePath,null)
                        .setCosXmlResultListener(object :CosXmlResultListener{
                            override fun onSuccess(request: CosXmlRequest?, result: CosXmlResult) {
                                Timber.i(result.printResult())
                                emitter.onNext("https://${result.accessUrl}")
                                emitter.onComplete()

                            }

                            override fun onFail(request: CosXmlRequest?, exception: CosXmlClientException?, serviceException: CosXmlServiceException?) {
                                emitter.onError(Throwable(exception?.message))
                            }

                        })

//                val bmobFile = BmobFile(File(imagePath))
//                bmobFile.uploadblock(object : UploadFileListener() {
//                    override fun done(p0: BmobException?) {
//                        p0?.let {
//                            emitter.onError(it)
//                            return
//                        }
//                        emitter.onNext(bmobFile.fileUrl)
//                        emitter.onComplete()
//                    }
//                })
            }
        })
    }

    /**
     * 获取收藏夹
     */
    override fun getFavorite(): Observable<List<FavoriteInfo>> {
        return Observable.create(object : ObservableOnSubscribe<List<FavoriteInfo>> {
            override fun subscribe(emitter: ObservableEmitter<List<FavoriteInfo>>) {
                val bmobQuery = BmobQuery<FavoriteInfo>()
                val userInfo = BmobUser.getCurrentUser(UserInfo::class.java)
                bmobQuery.addWhereEqualTo("user", userInfo)
                bmobQuery.findObjects(object : FindListener<FavoriteInfo>() {
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

    /**
     * 添加收藏
     */
    override fun addImageCollectToFavorite(favorites: List<FavoriteInfo>, imageCollect: ImagesInfo): Observable<String> {
        return Observable.create(object : ObservableOnSubscribe<String> {
            override fun subscribe(emitter: ObservableEmitter<String>) {
                val userInfo = BmobUser.getCurrentUser(UserInfo::class.java)
                imageCollect.user = userInfo

//                favorites.forEach { favorite ->
//                         .favorite = favorite
//                }
                val favoritesRelation = BmobRelation()
                favorites.forEach {
                    favoritesRelation.add(it)
                }

                imageCollect.favorites = favoritesRelation
                imageCollect.save(object : SaveListener<String>() {
                    override fun done(p0: String?, p1: BmobException?) {
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

    /**
     * 根据收藏夹Id  获取图集
     */
    override fun getImageCollectByFavorite(favorite:FavoriteInfo): Observable<List<ImagesInfo>> {
        return Observable.create(object : ObservableOnSubscribe<List<ImagesInfo>> {
            override fun subscribe(emitter: ObservableEmitter<List<ImagesInfo>>) {
                val bmobQuery = BmobQuery<ImagesInfo>()
//                val favoritePoint = BmobPointer()
//                favoritePoint.objectId = favorite.objectId
                bmobQuery.addWhereEqualTo("favorites",BmobPointer(favorite))
                bmobQuery.findObjects(object : FindListener<ImagesInfo>() {
                    override fun done(p0: MutableList<ImagesInfo>?, p1: BmobException?) {
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