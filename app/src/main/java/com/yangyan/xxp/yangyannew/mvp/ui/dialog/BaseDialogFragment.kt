package com.yangyan.xxp.yangyannew.mvp.ui.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yangyan.xxp.yangyannew.R


/**
 * Created by zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2017/11/1
 * Description :
 */


abstract class BaseDialogFragment : DialogFragment() {

    protected abstract fun getResoureId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getResoureId(), container)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialogTransparent)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
        start()
        initListener()


    }


    protected open fun initListener() {}


    /**
     * 初始化数据
     */
    protected open fun initData() {}

    /**
     * 初始化 View
     */
    protected open fun initView() {}

    /**
     * 开始请求
     */
    protected open fun start() {}

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
    }

    override fun onResume() {
        super.onResume()
        // getDialog().getWindow().getAttributes().windowAnimations = R.style.dialogHomeListAnim;
        val dialogWindow = dialog.window
        val lp = dialogWindow!!.attributes
        dialogWindow.setGravity(Gravity.CENTER)
        dialogWindow.attributes = lp
    }

    /**
     * 调整dialog的size和位置
     *
     * @param x
     * @param y
     * @param gravity
     */
    protected fun adjustSizeGravity(x: Int, y: Int, gravity: Int) {
        val dialogWindow = dialog.window
        val lp = dialogWindow!!.attributes
        lp.width = if (x == -1) lp.width else x
        lp.height = if (y == -1) lp.height else y
        dialogWindow.setGravity(gravity)
        dialogWindow.attributes = lp
    }

}
