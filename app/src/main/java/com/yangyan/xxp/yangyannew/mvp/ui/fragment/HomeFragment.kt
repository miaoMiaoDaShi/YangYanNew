package com.yangyan.xxp.yangyannew.mvp.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.paginate.Paginate
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.di.component.DaggerHomeComponent
import com.yangyan.xxp.yangyannew.di.module.HomeModule
import com.yangyan.xxp.yangyannew.mvp.contract.HomeContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.presenter.HomePresenter
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.HomeAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description :主页面
 */
class HomeFragment : BaseFragment<HomePresenter>(), HomeContract.View, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var mLayoutManager: LinearLayoutManager
    @Inject
    lateinit var mAdapter: HomeAdapter
    /**
     * 正在加载更多(一定要与刷新加载区分开)
     */
    private var mIsLoadMoreing = false

    override fun startLoadMore() {
        mIsLoadMoreing = true

    }

    override fun endLoadMore() {
        mIsLoadMoreing = false
    }

    override fun getContext(): Context = super.getContext()!!

    override fun showLoading() {
        // mSwipeRefreshLayout.isRefreshing = true
    }

    override fun launchActivity(intent: Intent) {
    }

    override fun hideLoading() {
        mSwipeRefreshLayout.isRefreshing = false
    }

    override fun killMyself() {
    }

    override fun showMessage(message: String) {
    }


    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerHomeComponent
                .builder()
                .appComponent(appComponent)
                .homeModule(HomeModule(this))
                .build()
                .inject(this)


    }

    override fun onRefresh() {
        mPresenter?.getHomeData(true)
    }

    override fun initData(savedInstanceState: Bundle?) {
        initRecyclerView()
        mSwipeRefreshLayout.setOnRefreshListener(this)
        //onRefresh()
        initPaginate()
    }

    private fun initPaginate() {
        Paginate.with(mRvHome, object : Paginate.Callbacks {
            override fun onLoadMore() {
                mPresenter?.getHomeData(false)
            }

            override fun isLoading(): Boolean = mIsLoadMoreing

            override fun hasLoadedAllItems(): Boolean = false
        })
                .setLoadingTriggerThreshold(4)
                .build()
    }

    private fun initRecyclerView() {
        mRvHome.layoutManager = mLayoutManager
        mRvHome.adapter = mAdapter
        mAdapter.setOnItemClickListener { view, viewType, data, position ->
            kotlin.run {
                val imagesInfo = data as ImagesInfo
                mPresenter?.getIamgeCollection(data.id)
            }
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun setData(data: Any?) {
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

}