package com.yangyan.xxp.yangyannew.mvp.ui.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.di.component.DaggerMineComponent
import com.yangyan.xxp.yangyannew.di.module.MineModule
import com.yangyan.xxp.yangyannew.mvp.contract.MineContract
import com.yangyan.xxp.yangyannew.mvp.presenter.MinePresenter
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.MineCollectAdapter
import kotlinx.android.synthetic.main.fragment_mine.*
import timber.log.Timber
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description :
 */
class MineFragment : BaseFragment<MinePresenter>(), MineContract.View {


    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager
    @Inject
    lateinit var mAdapter: MineCollectAdapter

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerMineComponent.builder()
                .appComponent(appComponent)
                .mineModule(MineModule(this))
                .build()
                .inject(this)
    }

    override fun initData(savedInstanceState: Bundle?) {
        initRecyclerView()
        mPresenter?.getCollectList()
    }

    private fun initRecyclerView() {
        mRvMineCollect.layoutManager = mLinearLayoutManager
        mRvMineCollect.adapter = mAdapter
        mRvMineCollect.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.top = ArmsUtils.dip2px(context, 10f)
                outRect.left = ArmsUtils.dip2px(context, 10f)
                outRect.right = ArmsUtils.dip2px(context, 10f)
                Timber.i("当前/共: ${parent.getChildLayoutPosition(view)}/${parent.layoutManager.childCount}")
                if (parent.getChildLayoutPosition(view) == parent.layoutManager.childCount) {
                    outRect.bottom = ArmsUtils.dip2px(context, 10f)
                }
            }
        })
    }

    override fun setData(data: Any?) {
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    override fun getContext(): Context = super.getContext()!!

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

    companion object {
        fun newInstance() = MineFragment()
    }
}