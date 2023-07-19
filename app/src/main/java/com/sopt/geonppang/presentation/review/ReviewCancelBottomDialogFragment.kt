package com.sopt.geonppang.presentation.review

import android.os.Bundle
import android.view.View
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.DialogBottomReviewWritingCancelBinding
import com.sopt.geonppang.util.binding.BindingBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewCancelBottomDialogFragment :
    BindingBottomSheetDialogFragment<DialogBottomReviewWritingCancelBinding>(R.layout.dialog_bottom_review_writing_cancel) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addListeners()
    }

    private fun addListeners() {
        binding.tvContinue.setOnClickListener {
            dismiss()
        }

        binding.tvStop.setOnClickListener {
            // TODO 상세뷰로 이동
        }
    }
}
