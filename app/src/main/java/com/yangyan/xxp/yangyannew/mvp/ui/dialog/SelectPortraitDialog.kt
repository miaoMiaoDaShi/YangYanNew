package com.yangyan.xxp.yangyannew.mvp.ui.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.view.ViewGroup
import com.jess.arms.utils.ArmsUtils
import com.yangyan.xxp.yangyannew.R

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/11/20
 * Description : 选择头像的对话框
 */
class SelectPortraitDialog : BaseDialogFragment (){
    override fun getResoureId(): Int {
        return  R.layout.dialog_select_portrait
    }
    override fun onResume() {
        super.onResume()
        val dialogWindow = dialog.window
        val lp = dialogWindow!!.attributes
        lp.width =ViewGroup.LayoutParams.MATCH_PARENT
        lp.height = ArmsUtils.dip2px(context!!,500f)
        dialogWindow.attributes = lp
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}