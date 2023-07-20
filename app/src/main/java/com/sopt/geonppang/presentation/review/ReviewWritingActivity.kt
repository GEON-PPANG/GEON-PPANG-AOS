package com.sopt.geonppang.presentation.review

import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityReviewWritingBinding
import com.sopt.geonppang.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewWritingActivity :
    BindingActivity<ActivityReviewWritingBinding>(R.layout.activity_review_writing) {
    private val viewModel: ReviewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        addListeners()
    }

    private fun addListeners() {
        binding.etWriteYourReview.setOnClickListener {
            binding.layoutScrollView.smoothScrollTo(0, binding.etWriteYourReview.top)
        }

        binding.etWriteYourReview.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.layoutScrollView.smoothScrollTo(0, binding.etWriteYourReview.top)
            }
        }

        binding.btnReviewSuccess.setOnClickListener {
            showReviewSuccessDialog()
        }

        binding.imgBackArrow.setOnClickListener {
            showReviewCancelDialog()
        }
    }

    private fun showReviewSuccessDialog() {
        val bakeryId = intent.getIntExtra(BAKERY_ID, -1)
        val reviewSuccessDialogFragment = ReviewSuccessBottomDialogFragment().apply {
            arguments = Bundle().apply {
                bakeryId?.let { bakeryId ->
                    putInt(BAKERY_ID, bakeryId)
                }
            }
        }
        reviewSuccessDialogFragment.show(supportFragmentManager, "reviewSuccessDialog")
    }

    private fun showReviewCancelDialog() {
        ReviewCancelBottomDialogFragment().show(supportFragmentManager, "reviewCancelDialog")
    }

    companion object {
        const val BAKERY_ID = "bakeryId"
    }
}
