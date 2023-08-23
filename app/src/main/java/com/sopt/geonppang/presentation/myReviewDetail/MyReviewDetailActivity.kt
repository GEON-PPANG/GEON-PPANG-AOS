package com.sopt.geonppang.presentation.myReviewDetail

import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityMyReviewDetailBinding
import com.sopt.geonppang.presentation.model.MyReviewBakeryInfo
import com.sopt.geonppang.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyReviewDetailActivity :
    BindingActivity<ActivityMyReviewDetailBinding>(R.layout.activity_my_review_detail) {
    private val myReviewDetailViewModel: MyReviewDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = myReviewDetailViewModel
        binding.lifecycleOwner = this

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        val reviewId = intent.getIntExtra(REVIEW_ID, -1)
        myReviewDetailViewModel.fetchMyReviewDetail(reviewId)
        val bakeryInfo = intent.getParcelableExtra<MyReviewBakeryInfo>(BAKERY_INFO)
        bakeryInfo?.let { bakeryInfo ->
            myReviewDetailViewModel.setUserInfo(bakeryInfo)
        }
    }

    private fun addListeners() {
        binding.toolbar.ivBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val REVIEW_ID = "reviewId"
        const val BAKERY_INFO = "bakeryInfo"
    }
}
