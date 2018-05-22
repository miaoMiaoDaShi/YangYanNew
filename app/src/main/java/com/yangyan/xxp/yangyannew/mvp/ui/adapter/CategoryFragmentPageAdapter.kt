package com.yangyan.xxp.yangyannew.mvp.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import com.yangyan.xxp.yangyannew.mvp.model.entity.CategoryInfo
import com.yangyan.xxp.yangyannew.mvp.ui.fragment.CategoryChildFragment
import com.yangyan.xxp.yangyannew.utils.CategoryUtils
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/22
 * Description :
 */
class CategoryFragmentPageAdapter
constructor(val fragmentManager: FragmentManager)
    : FragmentStatePagerAdapter(fragmentManager) {
    val mCategoryDatas by lazy {
        mutableListOf<CategoryInfo>()
                .apply {
                    CategoryUtils.categoryNameMap.mapKeys {
                        this.add(CategoryInfo(it.value, it.key))
                    }
                }
    }
    val mCategoryChildFragment by lazy {
        mutableListOf<CategoryChildFragment>()
                .apply {
                    mCategoryDatas.forEach {
                        this.add(CategoryChildFragment.newInstance(it.categoryCode))
                    }
                }
    }


    override fun getItem(position: Int): Fragment = mCategoryChildFragment[position]

    override fun getCount(): Int = mCategoryChildFragment.size

    override fun getPageTitle(position: Int): CharSequence? {
        return mCategoryDatas[position].categoryName
    }
}