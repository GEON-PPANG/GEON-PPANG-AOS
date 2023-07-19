package com.sopt.geonppang.presentation.review

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.DialogBottomReviewWritingSuccessBinding
import com.sopt.geonppang.util.binding.BindingBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewSuccessBottomDialogFragment :
    BindingBottomSheetDialogFragment<DialogBottomReviewWritingSuccessBinding>(R.layout.dialog_bottom_review_writing_success) {
    private val viewModel: ReviewViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this

        addListeners()
    }

    private fun addListeners() {
        binding.btnReviewWritingSuccess.setOnClickListener {
            val bakeryId = arguments?.getInt(BAKERY_ID, -1)
            Log.d("aaaa", bakeryId.toString())
            bakeryId?.let { bakeryId ->
                viewModel.writeReview(
                    bakeryId
                )
            }
            dismiss()
        }
    }

    companion object {
        const val BAKERY_ID = "bakeryId"
    }
}
