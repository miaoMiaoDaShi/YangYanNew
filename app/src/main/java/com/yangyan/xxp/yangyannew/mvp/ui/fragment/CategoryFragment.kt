package com.yangyan.xxp.yangyannew.mvp.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.di.component.DaggerCategoryComponent
import com.yangyan.xxp.yangyannew.di.module.CategoryModule
import com.yangyan.xxp.yangyannew.mvp.contract.CategoryChildContract
import com.yangyan.xxp.yangyannew.mvp.contract.CategoryContract
import com.yangyan.xxp.yangyannew.mvp.presenter.CategoryChildPresenter
import com.yangyan.xxp.yangyannew.mvp.presenter.CategoryPresenter
import com.yangyan.xxp.yangyannew.mvp.presenter.HomePresenter
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.CategoryFragmentPageAdapter
import kotlinx.android.synthetic.main.fragment_category.*
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description :分类页面
 */
class CategoryFragment : BaseFragment<CategoryPresenter>(), CategoryContract.View {
    override fun getSupportFragmentManager(): FragmentManager {
        return super.getFragmentManager()!!
    }

    override fun showLoading() {
    }

    override fun launchActivity(intent: Intent) {
    }

    override fun hideLoading() {
    }

    override fun killMyself() {
    }

    override fun showMessage(message: String) {
    }

    @Inject
    lateinit var mCategoryFragmentPageAdapter: CategoryFragmentPageAdapter

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerCategoryComponent
                .builder()
                .appComponent(appComponent)
                .categoryModule(CategoryModule(this))
                .build()
                .inject(this)
    }

    override fun initData(savedInstanceState: Bundle?) {
        setupTab()
    }

    private fun setupTab() {
        mVpCategory.adapter = mCategoryFragmentPageAdapter
        mTabCategory.setupWithViewPager(mVpCategory)
    }

    override fun setData(data: Any?) {
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    companion object {
        fun newInstance() = CategoryFragment()
    }
}