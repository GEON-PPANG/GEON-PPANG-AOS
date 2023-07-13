package com.sopt.geonppang.presentation.detail

import android.os.Bundle
import androidx.activity.viewModels
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
        detailBakeryInfoAdapter.submitList(viewModel.mockBakeryInfo)

        binding.rvDetail.adapter = detailBakeryInfoAdapter
        binding.bakeryInfo = viewModel.mockBakeryInfo[0]
    }
}
