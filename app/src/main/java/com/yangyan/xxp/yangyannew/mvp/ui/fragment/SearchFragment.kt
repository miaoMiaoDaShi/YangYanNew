package com.yangyan.xxp.yangyannew.mvp.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.app.onClick
import com.yangyan.xxp.yangyannew.app.visible
import com.yangyan.xxp.yangyannew.di.component.DaggerSearchComponent
import com.yangyan.xxp.yangyannew.di.module.SearchModule
import com.yangyan.xxp.yangyannew.mvp.contract.SearchContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.presenter.SearchPresenter
import com.yangyan.xxp.yangyannew.mvp.ui.activity.ImageCollectionActivity
import com.yangyan.xxp.yangyannew.mvp.ui.activity.MainActivity
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.SearchAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description :  搜索页
 */
class SearchFragment : BaseFragment<SearchPresenter>(), SwipeRefreshLayout.OnRefreshListener
        , SearchContract.View, BaseQuickAdapter.RequestLoadMoreListener {


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
        mProbar.visible(true)
        // mSwipeRefreshLayout.isRefreshing = true
    }

    override fun launchActivity(intent: Intent) {
    }

    override fun hideLoading() {
        mProbar.visibility = View.INVISIBLE
        mSwipeRefreshLayout.isRefreshing = false
    }

    override fun killMyself() {
    }

    override fun showMessage(message: String) {
    }


    @Inject
    lateinit var mLayoutManager: LinearLayoutManager
    @Inject
    lateinit var mAdapter: SearchAdapter

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerSearchComponent.builder()
                .appComponent(appComponent)
                .searchModule(SearchModule(this))
                .build()
                .inject(this)
    }

    override fun initData(savedInstanceState: Bundle?) {
        initRecyclerView()
        mSwipeRefreshLayout.setOnRefreshListener(this)
        bindListener()
    }

    private fun bindListener() {
        mIvClose.onClick {
            (activity as MainActivity).onBackPressed()
        }
        mEtKeyWords.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    //mTvTitle.visible(it.isEmpty())
                    onRefresh()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }


    override fun onRefresh() {
        mPresenter?.searchAtlasByKeyword(true, mEtKeyWords.text.toString().trim())

    }

    /**
     * 加载更多
     */
    override fun onLoadMoreRequested() {
        mPresenter?.searchAtlasByKeyword(false, mEtKeyWords.text.toString().trim())

    }


    private fun initRecyclerView() {
        mRvSearch.layoutManager = mLayoutManager
        mRvSearch.adapter = mAdapter
        mAdapter.setOnItemClickListener { adapter, view, position ->
            kotlin.run {
                val data = adapter.data[position] as ImagesInfo
                activity?.startActivity<ImageCollectionActivity>(
                       "data" to data
                )
            }
        }
        mAdapter.setEnableLoadMore(true)
        mAdapter.setOnLoadMoreListener(this, mRvSearch)

    }

    override fun setData(data: Any?) {
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    companion object {
        fun newInstance() = SearchFragment()
    }

}