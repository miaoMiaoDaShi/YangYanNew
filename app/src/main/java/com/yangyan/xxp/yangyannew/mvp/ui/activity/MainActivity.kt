package com.yangyan.xxp.yangyannew.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.Preconditions

import com.yangyan.xxp.yangyannew.di.component.DaggerMainComponent
import com.yangyan.xxp.yangyannew.di.module.MainModule
import com.yangyan.xxp.yangyannew.mvp.contract.MainContract
import com.yangyan.xxp.yangyannew.mvp.presenter.MainPresenter

import com.yangyan.xxp.yangyannew.R


import com.jess.arms.utils.Preconditions.checkNotNull
import com.yangyan.xxp.yangyannew.mvp.ui.fragment.CategoryFragment
import com.yangyan.xxp.yangyannew.mvp.ui.fragment.HomeFragment
import com.yangyan.xxp.yangyannew.mvp.ui.fragment.MineFragment
import com.yangyan.xxp.yangyannew.utils.AnalysisHTMLUtils
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup


class MainActivity : BaseActivity<MainPresenter>(), MainContract.View, BottomNavigationBar.OnTabSelectedListener {
    private val mHomeFragment by lazy {
        HomeFragment.newInstance()
    }
    private val mCategoryFragment by lazy {
        CategoryFragment.newInstance()
    }
    private val mMineFragment by lazy {
        MineFragment.newInstance()
    }

    private var mCurrentFragment: Fragment? = null

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mainModule(MainModule(this))
                .build()
                .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initData(savedInstanceState: Bundle?) {
        Observable.create(object : ObservableOnSubscribe<String> {
            override fun subscribe(emitter: ObservableEmitter<String>) {
                val content = Jsoup.connect("http://www.xxxiao.com/new/")
                        .get().body().html()
                val imagesInfos = AnalysisHTMLUtils.HomePageToList(content)
                for (imagesInfo in imagesInfos) {
                    println(imagesInfo.toString())
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .subscribe {

                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupPages()
    }

    /**
     * 三个页面的初始化
     */
    private fun setupPages() {
        mBNavBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
        mBNavBar.setMode(BottomNavigationBar.MODE_FIXED)
        mBNavBar
                .addItem(BottomNavigationItem(R.drawable.ic_tab_home, "首页")
                        .setActiveColorResource(android.R.color.black)
                        .setInActiveColorResource(R.color.colorTabInActive))
                .addItem(BottomNavigationItem(R.drawable.ic_tab_category, "分类")
                        .setActiveColorResource(android.R.color.black)
                        .setInActiveColorResource(R.color.colorTabInActive))
                .addItem(BottomNavigationItem(R.drawable.ic_tab_mine, "我的")
                        .setActiveColorResource(android.R.color.black)
                        .setInActiveColorResource(R.color.colorTabInActive))
                .setFirstSelectedPosition(0)
                .initialise()
        onTabSelected(0)
        mBNavBar.setTabSelectedListener(this)
    }

    /**
     * fragment的切换
     *
     * @param to
     */
    private fun switchFragment(to: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        Preconditions.checkNotNull(to)
        if (to !== mCurrentFragment) {
            if (mCurrentFragment == null) {
                transaction.add(R.id.mFlContent, to).commit()
            } else if (!to.isAdded) {
                transaction.hide(mCurrentFragment).add(R.id.mFlContent, to).commit()
            } else {
                transaction.hide(mCurrentFragment).show(to).commit()
            }
            mCurrentFragment = to
        }
    }

    override fun onTabReselected(position: Int) {
    }

    override fun onTabUnselected(position: Int) {
    }

    override fun onTabSelected(position: Int) {
        when (position) {
            0 -> {
                switchFragment(mHomeFragment)
            }
            1 -> {
                switchFragment(mCategoryFragment)
            }
            2 -> {
                switchFragment(mMineFragment)
            }
            else -> {
            }
        }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMessage(message: String) {
        checkNotNull(message)
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        checkNotNull(intent)
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {
        finish()
    }
}
