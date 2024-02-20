package com.sopt.geonppang.presentation.bakeryList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.DialogBottomBakeryListSortBinding
import com.sopt.geonppang.presentation.type.BakerySortType
import com.sopt.geonppang.util.AmplitudeUtils
import com.sopt.geonppang.util.binding.BindingBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BakeryListSortBottomSheetDialog :
    BindingBottomSheetDialogFragment<DialogBottomBakeryListSortBinding>(R.layout.dialog_bottom_bakery_list_sort) {
    private val viewModel: BakeryListViewModel by viewModels()
    private var bakerySortTypeListener: BakerySortTypeListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        addListeners()
        setBakerySortTypeSelected()
    }

    private fun addListeners() {
        // BakeryListFragment로 BakerySortType 전달
        binding.layoutDefault.setOnClickListener {
            bakerySortTypeListener?.onBakerySortTypeSelected(BakerySortType.DEFAULT)
            dismiss()
        }
        binding.layoutHighlyReviewed.setOnClickListener {
            AmplitudeUtils.trackEvent(CLICK_REVIEW_ARRAY)
            bakerySortTypeListener?.onBakerySortTypeSelected(BakerySortType.REVIEW)
            dismiss()
        }
    }

    // BakeryListFragment로부터 현재 정렬 type을 받아 옴
    private fun setBakerySortTypeSelected() {
        val data = arguments?.getString("bakerySortType")

        when (data) {
            BakerySortType.DEFAULT.sortType -> viewModel.setBakerySortType(BakerySortType.DEFAULT)
            BakerySortType.REVIEW.sortType -> viewModel.setBakerySortType(BakerySortType.REVIEW)
        }
    }

    // TODO: dana callback Listener 안쓰는 방식으로 수정해보기
    fun setDataListener(listener: BakerySortTypeListener) {
        bakerySortTypeListener = listener
    }

    companion object {
        const val CLICK_REVIEW_ARRAY = "click_reviewarray"
    }
}
