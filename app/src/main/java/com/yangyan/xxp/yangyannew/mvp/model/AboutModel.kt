package com.yangyan.xxp.yangyannew.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.yangyan.xxp.yangyannew.mvp.contract.AboutContract


@ActivityScope
class AboutModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), AboutContract.Model {
    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }
}
