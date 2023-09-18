package com.sopt.geonppang.presentation.reviewWriting

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityReviewWritingBinding
import com.sopt.geonppang.presentation.detail.DetailActivity
import com.sopt.geonppang.presentation.model.BakeryReviewWritingInfo
import com.sopt.geonppang.util.UiState
import com.sopt.geonppang.util.binding.BindingActivity
import com.sopt.geonppang.util.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ReviewWritingActivity :
    BindingActivity<ActivityReviewWritingBinding>(R.layout.activity_review_writing) {
    private val viewModel: ReviewWritingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initLayout()
        addListeners()
        collectData()

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
            viewModel.writeReview()
        }

        binding.toolbar.ivBack.setOnClickListener {
            showReviewCancelDialog()
        }

        binding.layoutParent.setOnClickListener {
            hideKeyboard(it)
        }

        binding.layoutScrollViewConstraint.setOnClickListener {
            hideKeyboard(it)
        }
    }

    private fun collectData() {
        viewModel.reviewSuccessState.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    showReviewSuccessDialog()
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)
        viewModel.reviewCancelState.flowWithLifecycle(lifecycle).onEach { reviewCancelState ->
            if (reviewCancelState == false) moveToDetail()
        }.launchIn(lifecycleScope)
    }

    private fun showReviewSuccessDialog() {
        ReviewSuccessBottomDialogFragment(::moveToDetail).show(supportFragmentManager, "reviewSuccessDialog")
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
