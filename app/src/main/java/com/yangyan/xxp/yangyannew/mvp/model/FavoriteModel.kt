package com.yangyan.xxp.yangyannew.mvp.model

import android.content.Context
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.datatype.BmobPointer
import cn.bmob.v3.datatype.BmobRelation
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener
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
import top.zibin.luban.Luban
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/29
 * Description : 收藏相关类---强行继承使用
 */
abstract class FavoriteModel
constructor(val repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), FavoriteContract.Model {

    @Inject
    lateinit var mCosXmlSimpleService: CosXmlSimpleService

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
        val userInfo = BmobUser.getCurrentUser(UserInfo::class.java)
        //关联用户
        imageCollect.users = BmobRelation(userInfo)
        val favoritesRelation = BmobRelation()
        //关联收藏夹
        favorites.forEach {
            favoritesRelation.add(it)
        }
        imageCollect.favorites = favoritesRelation
        return getImageCollectByIdAndFavorite(favorites, imageCollect.id)
                .flatMap { imagesInfosFromFavoriteAndId ->
                    //为空就是没有收藏,,根据图集的id去查
                    if (imagesInfosFromFavoriteAndId.isNullOrEmpty()) {
                        getImageCollectById(imageCollect.id)
                                .flatMap { imagesInfosFromId ->
                                    //用id去查还是没有,那就增加该图集到数据库
                                    if (imagesInfosFromId.isNullOrEmpty()) {
                                        Observable.create(object : ObservableOnSubscribe<String> {
                                            override fun subscribe(emitter: ObservableEmitter<String>) {
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
                                    } else {
                                        //收藏表中是有这个图集的   那就只需要关联当前用户  收藏夹
                                        getImageObjectIdById(imageCollect.id)
                                                .flatMap {objectId->
                                                    imageCollect.objectId = objectId
                                                    Observable.create(object : ObservableOnSubscribe<String> {
                                                        override fun subscribe(emitter: ObservableEmitter<String>) {
                                                            //更新数据
                                                            imageCollect.update(object : UpdateListener() {
                                                                override fun done(p0: BmobException?) {
                                                                    p0?.let {
                                                                        emitter.onError(it)
                                                                        return
                                                                    }
                                                                    emitter.onNext("200")
                                                                    emitter.onComplete()
                                                                }

                                                            })
                                                        }
                                                    })
                                                }

                                    }

                                }
                    } else {
                        //收藏表中是有这个图集的   并且是关联当前用户了的    那就只需要增加关联收藏夹
                        getImageObjectIdById(imageCollect.id)
                                .flatMap {objectId->
                                    imageCollect.objectId = objectId
                                    Observable.create(object : ObservableOnSubscribe<String> {
                                        override fun subscribe(emitter: ObservableEmitter<String>) {
                                            //更新数据
                                            imageCollect.update(object : UpdateListener() {
                                                override fun done(p0: BmobException?) {
                                                    p0?.let {
                                                        emitter.onError(it)
                                                        return
                                                    }
                                                    emitter.onNext("200")
                                                    emitter.onComplete()
                                                }

                                            })
                                        }
                                    })
                                }
                    }

                }

    }


    /**
     * 根据图集的id,查询图集信息
     */
    override fun getImageCollectById(id: Int): Observable<List<ImagesInfo>> {
        return Observable.create { emitter ->
            val bmobQuery = BmobQuery<ImagesInfo>()
            bmobQuery.addWhereEqualTo("id", id)
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
    }

    /**
     * 根据图集的id,收藏夹 ,查询图集信息
     */
    override fun getImageCollectByIdAndFavorite(favorites: List<FavoriteInfo>, id: Int): Observable<List<ImagesInfo>> {
        return Observable.create { emitter ->
            val bmobQuery = BmobQuery<ImagesInfo>()
            bmobQuery.addWhereEqualTo("id", id)
            val bmobQuerys = mutableListOf<BmobQuery<ImagesInfo>>()
            favorites.forEach { favorite ->
                bmobQuerys.add(BmobQuery<ImagesInfo>().apply { addWhereRelatedTo("favorites", BmobPointer(favorite)) })
            }
            bmobQuery.and(bmobQuerys)
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
    }


    /**
     * 根据图集的id &用户,查询图集信息
     */
    override fun getImageCollectByIdAndUser(id: Int, userInfo: UserInfo): Observable<List<ImagesInfo>> {
        return Observable.create { emitter ->
            val bmobQuery = BmobQuery<ImagesInfo>()
            bmobQuery.addWhereEqualTo("users", BmobPointer(userInfo))
            bmobQuery.and(mutableListOf(BmobQuery<ImagesInfo>().apply { addWhereEqualTo("id", id) }))
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
    }

    /**
     * 获取图集关联的收藏夹
     */
    override fun getFavoritesByImageCollectId(id: Int): Observable<List<FavoriteInfo>> {
        Timber.i("图集id: ${id}")
        return getImageObjectIdById(id)
                .flatMap { objectId ->
                    if (objectId.isEmpty()) {
                        Observable.just(listOf<FavoriteInfo>())
                    } else {
                        Observable.create { emitter ->
                            val bmobQuery = BmobQuery<FavoriteInfo>()
                            bmobQuery.addWhereRelatedTo("favorites", BmobPointer(ImagesInfo().apply { this.objectId = objectId }))
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
                    }
                }

    }

    /**
     * 根据图集的id 获取(如果有 ) bmob的imageInfo表中对应的objectId
     */
    private fun getImageObjectIdById(id: Int): Observable<String> {
        return Observable.create { emitter ->
            val bmobQuery = BmobQuery<ImagesInfo>()
            bmobQuery.addWhereEqualTo("id", id)
            bmobQuery.findObjects(object : FindListener<ImagesInfo>() {
                override fun done(p0: MutableList<ImagesInfo>?, p1: BmobException?) {
                    p0?.let {
                        emitter.onNext((if (p0.isEmpty()) "" else it[0].objectId))
                        emitter.onComplete()
                        return
                    }
                    p1?.let {
                        emitter.onError(it)
                    }
                }
            })
        }
    }

    /**
     * 删除该收藏夹下的某个图片
     * 其实就是取消关联嘛
     */
    override fun delImageCollectByFavorite(favorites: List<FavoriteInfo>, imageCollect: ImagesInfo): Observable<String> {
        return getImageObjectIdById(imageCollect.id)
                .flatMap { objectId ->
                    imageCollect.objectId = objectId
                    Observable.create(ObservableOnSubscribe<String> { emitter ->
                        favorites.forEach {
                            imageCollect.favorites = BmobRelation().apply { remove(it) }
                        }
                        imageCollect.update(object : UpdateListener() {
                            override fun done(p0: BmobException?) {
                                p0?.let {
                                    emitter.onError(it)
                                    return
                                }
                                emitter.onNext("200")
                                emitter.onComplete()
                            }

                        })
                    })
                }
    }

    /**
     * 根据收藏夹Id  获取图集
     */
    override fun getImageCollectByFavorite(favorite: FavoriteInfo): Observable<List<ImagesInfo>> {
        return Observable.create { emitter ->
            val bmobQuery = BmobQuery<ImagesInfo>()
            bmobQuery.addWhereEqualTo("favorites", BmobPointer(favorite))
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
    }

    /**
     * 删除收藏夹
     */
    override fun delFavorite(favoriteInfo: FavoriteInfo, imageInfos: List<ImagesInfo>): Observable<Int> {
        val userInfo = BmobUser.getCurrentUser(UserInfo::class.java)
        return Observable.create(ObservableOnSubscribe<Int> { emitter ->
            favoriteInfo.user = userInfo
            favoriteInfo.delete(object : UpdateListener() {
                override fun done(p0: BmobException?) {
                    p0?.let {
                        emitter.onError(it)
                        return
                    }
                    emitter.onNext(200)
                    emitter.onComplete()
                }
            })
        })

                .flatMap {
                    var modSize = 0
                    Observable.fromIterable(imageInfos)
                            .flatMap { imageInfo ->
                                modSize++
                                addImageCollectToFavorite(listOf(FavoriteInfo().apply { objectId = userInfo.defaultFavoriteId }), imageInfo)
                            }
                            .map { modSize }
                }

    }


    /**
     * 添加收藏夹
     */
    override fun addFavorite(favoriteInfo: FavoriteInfo): Observable<String> {
        return Observable.create { emitter ->
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
    }


    /**
     * 上传封面
     */
    override fun uploadCover(imagePath: String, context: Context): Observable<String> {
        return Observable.create { emitter ->
            val compressImageFile = Luban.with(context).load(imagePath).get()[0]
            TransferManager(mCosXmlSimpleService, TransferConfig.Builder().build())
                    .upload("yang-yan-new-1252246683",
                            "${System.currentTimeMillis()}-${compressImageFile.name}",
                            compressImageFile.absolutePath, null)
                    .setCosXmlResultListener(object : CosXmlResultListener {
                        override fun onSuccess(request: CosXmlRequest?, result: CosXmlResult) {
                            Timber.i(result.printResult())
                            emitter.onNext("https://${result.accessUrl}")
                            emitter.onComplete()

                        }

                        override fun onFail(request: CosXmlRequest?, exception: CosXmlClientException?, serviceException: CosXmlServiceException?) {
                            emitter.onError(Throwable(exception?.message))
                        }

                    })
        }
    }


}