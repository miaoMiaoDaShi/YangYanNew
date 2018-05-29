package com.yangyan.xxp.yangyannew.mvp.ui.fragment

import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jess.arms.utils.ArmsUtils
import com.yangyan.xxp.yangyannew.R
import com.yangyan.xxp.yangyannew.app.onClick
import com.yangyan.xxp.yangyannew.di.component.ImageCollectionComponent
import com.yangyan.xxp.yangyannew.mvp.model.entity.FavoriteInfo
import com.yangyan.xxp.yangyannew.mvp.presenter.FavoritePresenter
import com.yangyan.xxp.yangyannew.mvp.presenter.ImageCollectionPresenter
import com.yangyan.xxp.yangyannew.mvp.ui.adapter.MineFavoriteAdapter
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_sheet_collect_to_favorite.*
import timber.log.Timber
import javax.inject.Inject

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/29
 * Description : 图集页面  点击收藏
 */
class FavoriteBottomSheetFragment : BottomSheetDialogFragment() {
    @Inject
    lateinit var mAdapter: MineFavoriteAdapter
    @Inject
    lateinit var mLayoutManager: LinearLayoutManager
    @Inject
    lateinit var mPresenter: ImageCollectionPresenter
    @Inject
    lateinit var mFavoriteDatas: List<FavoriteInfo>

    private var mDoneBlock: ((favoriteDatas:List<FavoriteInfo>) -> Unit)? = null

    private var mImageCollectionComponent: ImageCollectionComponent? = null

    private var mView: View? = null
    private var mViewIsPre = false
    private val mCheckedFavorite = mutableListOf<FavoriteInfo>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_sheet_collect_to_favorite, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        if (mViewIsPre) {
            return
        }

        if (mImageCollectionComponent != null && mView != null) {
            mViewIsPre = true
            mRvFavorite.layoutManager = mLayoutManager
            mRvFavorite.adapter = mAdapter
            mRvFavorite.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.top = ArmsUtils.dip2px(context, 10f)
                    outRect.left = ArmsUtils.dip2px(context, 10f)
                    outRect.right = ArmsUtils.dip2px(context, 10f)
                    Timber.i("当前/共: ${parent.getChildLayoutPosition(view)}/${parent.layoutManager.childCount}")
                    if (parent.getChildLayoutPosition(view) == parent.layoutManager.childCount) {
                        outRect.bottom = ArmsUtils.dip2px(context, 10f)
                    }
                }
            })
            mAdapter.setOnItemClickListener { view, viewType, data, position ->
                kotlin.run {
                    data as FavoriteInfo
                    data.isChecked = !data.isChecked
                    if (data.isChecked) {
                        if (!mCheckedFavorite.contains(data)) {
                            mCheckedFavorite.add(data)
                        }
                    } else {
                        if (mCheckedFavorite.contains(data)) {
                            mCheckedFavorite.remove(data)
                        }
                    }
                    mAdapter.notifyItemChanged(position)

                    if (mFavoriteDatas.isEmpty()){
                        mTvDone.setTextColor(Color.GRAY)
                    } else{
                        mTvDone.setTextColor(Color.BLACK)
                    }
                }
            }
            mTvDone.onClick {
                if (mFavoriteDatas.isNotEmpty()) {
                    mDoneBlock?.invoke(mCheckedFavorite)
                } else{
                    Toasty.error(context!!,"请选择收藏夹").show()
                }
            }
        }

    }


    fun setDoneBlock(block: (favoriteDatas:List<FavoriteInfo>) -> Unit) {
        mDoneBlock = block
    }

    fun setImageCollectionComponent(imageCollectionComponent: ImageCollectionComponent) {
        mImageCollectionComponent?.let {
            return
        }
        mImageCollectionComponent = imageCollectionComponent
        mImageCollectionComponent?.inject(this)

        setupView()
    }


    override fun onDestroyView() {
        super.onDestroyView()

    }

    override fun dismiss() {
        super.dismiss()
        mViewIsPre = false
    }

    companion object {
        fun newInstance(): FavoriteBottomSheetFragment {
            val fragment = FavoriteBottomSheetFragment()
            // val bundle = Bundle()
            return fragment
        }
    }
}