package com.yangyan.xxp.yangyannew.mvp.ui.activity

import android.content.Context
import com.jess.arms.base.BaseActivity
import com.jess.arms.mvp.IPresenter
import io.github.inflationx.viewpump.ViewPumpContextWrapper

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2019/1/9
 * Description :
 */
abstract  class YangYanBaseActivity<P:IPresenter>: BaseActivity<P>() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(base))
    }
}