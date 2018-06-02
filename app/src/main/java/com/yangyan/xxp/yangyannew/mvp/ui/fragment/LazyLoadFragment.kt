package com.yangyan.xxp.yangyannew.mvp.ui.fragment

import android.os.Bundle
import android.view.View
import com.jess.arms.base.BaseFragment
import com.jess.arms.mvp.IPresenter

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/22
 * Description : 懒加载的fragment
 */
abstract class LazyLoadFragment<P : IPresenter> : BaseFragment<P>() {
    /**
     * 视图是否加载完毕
     */
    private var isViewPrepare = false
    /**
     * 数据是否加载过了
     */
    private var hasLoadData = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepare = true
        lazyLoadDataIfPrepared()
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }


    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepare && !hasLoadData) {
            lazyLoad()
            hasLoadData = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hasLoadData = false
        isViewPrepare = false
    }

    protected abstract fun lazyLoad()
}