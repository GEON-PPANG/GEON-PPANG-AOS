package com.sopt.geonppang.presentation.reviewWriting

import android.os.Bundle
import android.view.View
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.DialogBottomReviewWritingSuccessBinding
import com.sopt.geonppang.util.binding.BindingBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewSuccessBottomDialogFragment(
    private val moveToDetail: () -> Unit
) :
    BindingBottomSheetDialogFragment<DialogBottomReviewWritingSuccessBinding>(R.layout.dialog_bottom_review_writing_success) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner

        isCancelable = false

        addListeners()
    }

    private fun addListeners() {
        binding.btnReviewWritingSuccess.setOnClickListener {
            dismiss()
            moveToDetail()
        }
    }
}
