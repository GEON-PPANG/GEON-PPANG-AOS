package com.sopt.geonppang.presentation.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityDetailBinding
import com.sopt.geonppang.util.binding.BindingActivity

class DetailActivity : BindingActivity<ActivityDetailBinding>(R.layout.activity_detail) {
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initLayout()
    }

    private fun initLayout() {
        val detailBakeryInfoAdapter = DetailBakeryInfoAdapter(this)
        val detailMenuAdapter = DetailMenuAdapter(this)
        val detailReviewDataAdapter = DetailReviewDataAdapter(this)
        val detailReviewAdapter = DetailReviewAdapter(this)
        val concatAdapter = ConcatAdapter(
            detailBakeryInfoAdapter,
            detailMenuAdapter,
            detailReviewDataAdapter,
            detailReviewAdapter
        )

        detailBakeryInfoAdapter.submitList(viewModel.mockBakeryInfo)
        detailMenuAdapter.submitList(viewModel.mockMenuList)
        detailReviewDataAdapter.submitList(viewModel.mockReviewData)
        detailReviewAdapter.submitList(viewModel.mockReview)

        binding.rvDetail.adapter = concatAdapter
        binding.bakeryInfo = viewModel.mockBakeryInfo[0]
    }
}
