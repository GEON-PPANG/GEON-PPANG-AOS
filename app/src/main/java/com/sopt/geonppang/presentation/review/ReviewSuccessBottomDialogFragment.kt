package com.sopt.geonppang.presentation.review

import android.os.Bundle
import android.view.View
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.DialogBottomReviewWritingSuccessBinding
import com.sopt.geonppang.util.binding.BindingBottomSheetDialogFragment

class ReviewSuccessBottomDialogFragment :
    BindingBottomSheetDialogFragment<DialogBottomReviewWritingSuccessBinding>(R.layout.dialog_bottom_review_writing_success) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addListeners()
    }

    private fun addListeners() {
        binding.btnReviewWritingSuccess.setOnClickListener {
            // TODO 상세뷰로 이동
            dismiss()
        }
    }
}
