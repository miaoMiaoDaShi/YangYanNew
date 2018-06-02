package com.yangyan.xxp.yangyannew.mvp.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.app.onClick
import com.yangyan.xxp.yangyannew.callback.onActivityBackCallback
import com.yangyan.xxp.yangyannew.di.component.DaggerHomeComponent
import com.yangyan.xxp.yangyannew.di.module.HomeModule
import com.yangyan.xxp.yangyannew.mvp.contract.HomeContract
import com.yangyan.xxp.yangyannew.mvp.model.entity.ImagesInfo
import com.yangyan.xxp.yangyannew.mvp.presenter.HomePresenter
import com.yangyan.xxp.yangyannew.mvp.ui.activity.ImageCollectionActivity
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.HomeAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description :主页面
 */
class HomeFragment : BaseFragment<HomePresenter>(), HomeContract.View,
        SwipeRefreshLayout.OnRefreshListener, onActivityBackCallback {


    @Inject
    lateinit var mLayoutManager: LinearLayoutManager
    @Inject
    lateinit var mAdapter: HomeAdapter
    @Inject
    lateinit var mSearchFragment: SearchFragment

    private var mSearchFragmentIsAdded = false

    private var mSearchFragmentIsShow = false

    private val mFragmentManager by lazy {
        fragmentManager
    }

    override fun startLoadMore() {

    }

    override fun endLoadMore() {
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
        mSwipeRefreshLayout.post {
            mSwipeRefreshLayout.isRefreshing = true
            onRefresh()
        }
        bindListener()
    }

    private fun bindListener() {
        mShadowView.onClick {
            //去搜索页面
            showSearchFragment()
        }
    }

    /**
     * 显示收藏页面
     */
    private fun showSearchFragment() {
        mSearchFragmentIsShow = true
        mFragmentManager?.let {
            it.beginTransaction().run {
                setCustomAnimations(R.anim.fragment_slide_up, 0)
                if (!mSearchFragmentIsAdded) {
                    add(android.R.id.content, mSearchFragment)
                    mSearchFragmentIsAdded = true
                }
                show(mSearchFragment).commit()
            }


        }
    }

    /**
     * 隐藏搜索页面
     */
    fun hideSearchFragment() {
        mSearchFragmentIsShow = false
        mFragmentManager?.let {
            val fragmentTransaction = it.beginTransaction()
            fragmentTransaction.setCustomAnimations(0, R.anim.fragment_slide_down)
            fragmentTransaction.hide(mSearchFragment).commit()
        }
    }


    private fun initRecyclerView() {
        mRvHome.layoutManager = mLayoutManager
        mRvHome.adapter = mAdapter.apply {
            setOnItemClickListener { view, viewType, position ->
                kotlin.run {
                    activity?.startActivity<ImageCollectionActivity>(
                            "data" to data[position]
                    )
                }
            }
            setEnableLoadMore(true)
            setOnLoadMoreListener({
                mPresenter?.getHomeData(false)
            }, mRvHome)
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

    override fun onBackPressed(): Boolean {
        return mSearchFragmentIsShow.apply {
            if (this) hideSearchFragment()
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

}