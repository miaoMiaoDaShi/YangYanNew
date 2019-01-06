package com.yangyan.xxp.yangyannew.mvp.ui.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.app.visible


/**
 * Created by zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2017/11/1
 * Description :
 */


abstract class BaseDialogFragment : DialogFragment() {
    protected var mContentView: View? = null
    protected abstract fun getResourcesId(): Int
    private val mOutAnimation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.dialog_modal_out)
    }
    private val mInAnimation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.dialog_modal_in)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContentView = inflater.inflate(getResourcesId(), container)
        return mContentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContentView?.startAnimation(mInAnimation)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialogTransparent)
        return object : Dialog(this.activity, this.theme) {
            override fun dismiss() {
                dismissWithAnimation()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setupLayoutParams()
    }

    override fun show(manager: FragmentManager?, tag: String?) {
        try {
            //在每个add事务前增加一个remove事务，防止连续的add
            manager?.beginTransaction()?.remove(this)?.commit()
            super.show(manager, tag)
        } catch (e: Exception) {
            //同一实例使用不同的tag会异常,这里捕获一下
            e.printStackTrace()
        }

    }

    open fun setupLayoutParams() {
        val dialogWindow = dialog.window
        val lp = dialogWindow!!.attributes
        dialogWindow.setGravity(Gravity.CENTER)
        dialogWindow.attributes = lp
    }


    fun dismissWithAnimation() {
        mContentView?.startAnimation(mOutAnimation
                .apply {
                    setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationRepeat(animation: Animation?) {
                        }

                        override fun onAnimationEnd(animation: Animation?) {
                            mContentView?.apply {
                                visible(false)
                                post {
                                    this@BaseDialogFragment.dismiss()
                                }
                            }


                        }

                        override fun onAnimationStart(animation: Animation?) {
                        }

                    })
                })
    }
}
