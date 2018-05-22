package com.yangyan.xxp.yangyannew.mvp.contract

import android.support.v4.app.FragmentManager
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.yangyan.xxp.yangyannew.mvp.model.entity.CategoryInfo
import io.reactivex.Observable

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description :分类
 */
interface CategoryContract {
    interface View : IView{
       fun getSupportFragmentManager():FragmentManager
    }
    interface Model : IModel
}