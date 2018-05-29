package com.yangyan.xxp.yangyannew.mvp.ui.fragment

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yangyan.xxp.yangyannew.R

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/29
 * Description : 图集页面  点击收藏
 */
class FavoriteBottomSheetFragment : BottomSheetDialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sheet_collect_to_favorite, container, false)
    }


    companion object {
        fun newInstance(): FavoriteBottomSheetFragment {
            val fragment = FavoriteBottomSheetFragment()
            return fragment
        }
    }
}