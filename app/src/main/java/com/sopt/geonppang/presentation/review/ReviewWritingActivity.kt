package com.sopt.geonppang.presentation.review

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityReviewWritingBinding
import com.sopt.geonppang.presentation.detail.DetailActivity
import com.sopt.geonppang.presentation.model.BakeryReviewWritingInfo
import com.sopt.geonppang.util.UiState
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

        initLayout()
        addListeners()
        addObservers()

        val bakeryId = intent.getIntExtra(BAKERY_ID, -1)
        viewModel.setBakeryId(bakeryId)
    }

    private fun initLayout() {
        val bakeryReviewWritingInfo =
            intent.getParcelableExtra<BakeryReviewWritingInfo>(BAKERY_INFO)
        bakeryReviewWritingInfo?.let { viewModel.setBakeryInfo(it) }
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

    private fun addObservers() {
        viewModel.reviewSuccessState.observe(this) {
            when (it) {
                is UiState.Success -> {
                    moveToDetail()
                }
                else -> {}
            }
        }
        viewModel.reviewCancelState.observe(this) { reviewCancelState ->
            if (!reviewCancelState) {
                moveToDetail()
            }
        }
    }

    private fun showReviewSuccessDialog() {
        ReviewSuccessBottomDialogFragment().show(supportFragmentManager, "reviewSuccessDialog")
    }

    private fun showReviewCancelDialog() {
        ReviewCancelBottomDialogFragment().show(supportFragmentManager, "reviewCancelDialog")
    }

    private fun moveToDetail() {
        val intent = Intent(this, DetailActivity::class.java)
        viewModel.bakeryId.value?.let { id ->
            intent.putExtra(BAKERY_ID, id)
        }
        startActivity(intent)
        finish()
    }

    companion object {
        const val BAKERY_ID = "bakeryId"
        const val BAKERY_INFO = "bakeryInfo"
    }
}
