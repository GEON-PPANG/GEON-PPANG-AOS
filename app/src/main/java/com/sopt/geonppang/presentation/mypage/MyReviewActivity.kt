package com.sopt.geonppang.presentation.mypage

import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityMyReviewBinding
import com.sopt.geonppang.util.binding.BindingActivity

class MyReviewActivity : BindingActivity<ActivityMyReviewBinding>(R.layout.activity_my_review) {
    private val viewModel: MyPageViewModel by viewModels()

    lateinit var myReviewAdapter: MyReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        myReviewAdapter = MyReviewAdapter()
        binding.rvMyReviewList.adapter = myReviewAdapter
        myReviewAdapter.submitList(viewModel.mockMyReviewList)
    }

    private fun addListeners(){
        binding.toolbar.ivBack.setOnClickListener {
            finish()
        }
    }
}
