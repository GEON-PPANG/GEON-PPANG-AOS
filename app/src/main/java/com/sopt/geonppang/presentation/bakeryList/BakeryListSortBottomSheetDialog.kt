package com.sopt.geonppang.presentation.bakeryList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.DialogBottomBakeryListSortBinding
import com.sopt.geonppang.presentation.type.BakerySortType
import com.sopt.geonppang.util.binding.BindingBottomSheetDialogFragment

class BakeryListSortBottomSheetDialog :
    BindingBottomSheetDialogFragment<DialogBottomBakeryListSortBinding>(R.layout.dialog_bottom_bakery_list_sort) {
    private val viewModel: BakeryListViewModel by viewModels()
    private var bakerySortTypeListener: BakerySortTypeListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        addListeners()
        setBakerySortTypeSelected()
    }

    private fun addListeners() {
        // BakeryListFragment로 BakerySortType 전달
        binding.layoutDefault.setOnClickListener {
            bakerySortTypeListener?.onBakerySortTypeSelected(BakerySortType.DEFAULT)
            dismiss()
        }
        binding.layoutReviewNum.setOnClickListener {
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

    fun setDataListener(listener: BakerySortTypeListener) {
        bakerySortTypeListener = listener
    }
}
