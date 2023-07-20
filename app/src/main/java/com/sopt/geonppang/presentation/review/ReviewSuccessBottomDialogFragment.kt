package com.sopt.geonppang.presentation.review

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.DialogBottomReviewWritingSuccessBinding
import com.sopt.geonppang.util.binding.BindingBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewSuccessBottomDialogFragment :
    BindingBottomSheetDialogFragment<DialogBottomReviewWritingSuccessBinding>(R.layout.dialog_bottom_review_writing_success) {
    private val viewModel: ReviewViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this

        addListeners()
    }

    private fun addListeners() {
        binding.btnReviewWritingSuccess.setOnClickListener {
            viewModel.writeReview()
            dismiss()
        }
    }
}
