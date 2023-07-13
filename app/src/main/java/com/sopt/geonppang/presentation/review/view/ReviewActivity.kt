package com.sopt.geonppang.presentation.review.view

import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityReviewBinding
import com.sopt.geonppang.presentation.review.viewmodel.ReviewViewModel
import com.sopt.geonppang.util.binding.BindingActivity
import com.sopt.geonppang.util.binding.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewActivity : BindingActivity<ActivityReviewBinding>(R.layout.activity_review) {
    private val viewModel: ReviewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        // binding에서 LiveData를 사용할 경우 해당 코드 필요
    }

    private fun initLayout() {
        TODO("Not yet implemented")
    }

    private fun addListeners() {
        binding.root.setOnClickListener {
            hideKeyboard(it)
        }
    }

    private fun addObservers() {
        TODO("Not yet implemented")
    }
}
