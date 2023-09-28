package com.sopt.geonppang.presentation.reviewWriting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.DialogBottomReviewWritingCancelBinding
import com.sopt.geonppang.util.AmplitudeUtils
import com.sopt.geonppang.util.binding.BindingBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewCancelBottomDialogFragment :
    BindingBottomSheetDialogFragment<DialogBottomReviewWritingCancelBinding>(R.layout.dialog_bottom_review_writing_cancel) {
    private val viewModel: ReviewWritingViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner

        addListeners()
    }

    private fun addListeners() {
        binding.btnContinue.setOnClickListener {
            AmplitudeUtils.trackEvent(CLICK_REVIEW_WRITING_CONTINUE)
            dismiss()
        }

        binding.btnStop.setOnClickListener {
            AmplitudeUtils.trackEvent(CLICK_REVIEW_WRITING_STOP)
            viewModel.setReviewCancelState(false)
        }
    }

    companion object {
        const val CLICK_REVIEW_WRITING_STOP = "click_reviewwriting_stop"
        const val CLICK_REVIEW_WRITING_CONTINUE = "click_reviewwriting_continue"
    }
}
