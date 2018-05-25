package com.yangyan.xxp.yangyannew.mvp.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jess.arms.di.component.AppComponent
import com.paginate.Paginate
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.di.component.DaggerCategoryChildComponent
import com.yangyan.xxp.yangyannew.di.module.CategoryChildModule
import com.yangyan.xxp.yangyannew.mvp.contract.CategoryChildContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.presenter.CategoryChildPresenter
import com.yangyan.xxp.yangyannew.mvp.ui.activity.ImageCollectionActivity
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.HomeAdapter
import kotlinx.android.synthetic.main.fragment_category_child.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/22
 * Description :
 */
class CategoryChildFragment : LazyLoadFragment<CategoryChildPresenter>(), CategoryChildContract.View
        , SwipeRefreshLayout.OnRefreshListener {
    override fun getContext(): Context = super.getContext()!!
    @Inject
    lateinit var mLayoutManager: LinearLayoutManager
    @Inject
    lateinit var mAdapter: HomeAdapter
    /**
     * 正在加载更多(一定要与刷新加载区分开)
     */
    private var mIsLoadMoreing = false

    private var mFristLoad = true

    override fun startLoadMore() {
        mIsLoadMoreing = true

    }

    override fun endLoadMore() {
        mIsLoadMoreing = false
    }

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

    override fun onRefresh() {
        mPresenter?.getCategoryData(arguments?.getString("categoryCode") ?: "tag", true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        mSwipeRefreshLayout.setOnRefreshListener(this)
        //onRefresh()
        initPaginate()
    }

    private fun initPaginate() {
        Paginate.with(mRvCategoryChild, object : Paginate.Callbacks {
            override fun onLoadMore() {
                if (mFristLoad) {
                    mFristLoad = false
                    return
                }
                mPresenter?.getCategoryData(arguments?.getString("categoryCode") ?: "tag", false)
            }

            override fun isLoading(): Boolean = mIsLoadMoreing

            override fun hasLoadedAllItems(): Boolean = false
        })
                .setLoadingTriggerThreshold(0)
                .build()
    }

    private fun initRecyclerView() {
        mRvCategoryChild.layoutManager = mLayoutManager
        mRvCategoryChild.adapter = mAdapter
        mAdapter.setOnItemClickListener { view, viewType, data, position ->
            kotlin.run {
                data as ImagesInfo
                activity?.startActivity<ImageCollectionActivity>(
                        "id" to data.id,
                        "category" to data.category,
                        "title" to data.title,
                        "cover" to data.HDImageUrl
                )
            }
        }

    }

    override fun lazyLoad() {
        onRefresh()
    }

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerCategoryChildComponent
                .builder()
                .appComponent(appComponent)
                .categoryChildModule(CategoryChildModule(this))
                .build()
                .inject(this)
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun setData(data: Any?) {
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_category_child, container, false)

    companion object {
        fun newInstance(categoryCode: String): CategoryChildFragment {
            val bundle = Bundle()
            bundle.putString("categoryCode", categoryCode)
            val fragment = CategoryChildFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}