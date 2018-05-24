package com.yangyan.xxp.yangyannew.mvp.ui.dialog

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import com.jess.arms.utils.ArmsUtils
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.app.onClick
import com.yangyan.xxp.yangyannew.mvp.ui.activity.LoginActivity
import kotlinx.android.synthetic.main.dialog_about.*
import org.jetbrains.anko.startActivity

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/24
 * Description : 关于对话框
 */
class AboutDialog : BaseDialogFragment() {
    override fun getResoureId(): Int {
        return R.layout.dialog_about
    }

    override fun initListener() {
        super.initListener()
        mBtnLoginOut.onClick {
            startActivity(Intent(context,LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
        }
        mIvClose.onClick {
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        val dialogWindow = dialog.window
        val lp = dialogWindow!!.attributes
        lp.width =ArmsUtils.dip2px(context,280f)
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialogWindow.attributes = lp
    }

    override fun initView() {
        super.initView()
        var verName = "1.1.1"
        context?.let {
            verName = it.packageManager.getPackageInfo(it.packageName, 0).versionName
        }
        mTvVerName.text = "Version $verName"
    }

    companion object {
        fun newInstance() = AboutDialog()
    }
}