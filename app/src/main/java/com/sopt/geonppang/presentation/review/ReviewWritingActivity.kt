package com.sopt.geonppang.presentation.review

import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityReviewWritingBinding
import com.sopt.geonppang.util.binding.BindingActivity
import com.sopt.geonppang.util.binding.hideKeyboard

class ReviewWritingActivity :
    BindingActivity<ActivityReviewWritingBinding>(R.layout.activity_review_writing) {
    private val viewModel: ReviewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        hideKeyboard(binding.root)
        return super.dispatchTouchEvent(ev)
    }
}
