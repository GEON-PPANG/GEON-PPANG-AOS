package com.sopt.geonppang.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.FragmentHomeBinding
import com.sopt.geonppang.util.binding.BindingFragment

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModels()

    lateinit var bestBakeryAdapter: BestBakeryAdapter
    lateinit var bestReviewAdapter: BestReviewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
    }

    private fun initLayout() {
        bestBakeryAdapter = BestBakeryAdapter()
        binding.layoutHomeBestBakeryList.adapter = bestBakeryAdapter
        bestBakeryAdapter.submitList(viewModel.mockBestBakeryList)

        bestReviewAdapter = BestReviewAdapter()
        binding.layoutHomeBestReviewList.adapter = bestReviewAdapter
        bestReviewAdapter.submitList(viewModel.mockBestReviewList)
    }
}
