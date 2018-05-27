package com.yangyan.xxp.yangyannew.di.component

import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.scope.FragmentScope
import com.yangyan.xxp.yangyannew.di.module.SearchModule
import com.yangyan.xxp.yangyannew.mvp.model.SearchModel
import com.yangyan.xxp.yangyannew.mvp.ui.fragment.SearchFragment
import dagger.Component

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/26
 * Description : 搜索页面
 */
@FragmentScope
@Component(modules = arrayOf(SearchModule::class),dependencies = arrayOf(AppComponent::class))
interface SearchComponent {
    fun inject(fragment:SearchFragment)
}