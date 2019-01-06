package com.yangyan.xxp.yangyannew.mvp.ui.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.jess.arms.utils.ArmsUtils
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.app.onClick
import kotlinx.android.synthetic.main.dialog_warning.*

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2019/1/4
 * Description :
 */
class WarningDialog() : BaseDialogFragment() {

    private var mContent = ""
    private var mDes = ""
    private var mConfirmText = "确认"
    private var mCancelText = "取消"
    private var mConfirmFunc: (() -> Unit)? = null
    private var mCancelFunc: (() -> Unit)? = null
    private var mClickDismiss = true
    override fun getResourcesId(): Int {
        return R.layout.dialog_warning
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mTvContent.text = mContent
        mTvDes.text = mDes

        mBtnConfirm.apply {
            text = mConfirmText
            onClick {
                mConfirmFunc?.invoke()
                if (mClickDismiss) {
                    dismissWithAnimation()
                }
            }
        }
        mBtnCancel.apply {
            text = mCancelText
            onClick {
                mCancelFunc?.invoke()
                if (mClickDismiss) {
                    dismissWithAnimation()
                }
            }
        }


    }

    override fun setupLayoutParams() {
        val dialogWindow = dialog.window
        val lp = dialogWindow!!.attributes
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialogWindow.attributes = lp
    }

    fun content(content: String): WarningDialog {
        mContent = content
        return this
    }

    fun des(des: String): WarningDialog {
        mDes = des
        return this
    }

    fun confirmButton(text: String, func: (() -> Unit)? = null): WarningDialog {
        mConfirmText = text
        mConfirmFunc = func
        return this
    }

    fun cancelButton(text: String, func: (() -> Unit)? = null): WarningDialog {
        mCancelText = text
        mCancelFunc = func
        return this
    }

    fun clickDismiss(boolean: Boolean): WarningDialog {
        mClickDismiss = boolean
        return this
    }

    fun show(fragmentManager: FragmentManager, func: WarningDialog.() -> Unit): WarningDialog {
        this.func()
        this.show(fragmentManager, "warningDialog")
        return this
    }


}