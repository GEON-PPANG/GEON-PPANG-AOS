package com.sopt.geonppang.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.FragmentHomeBinding
import com.sopt.geonppang.presentation.search.SearchActivity
import com.sopt.geonppang.util.binding.BindingFragment

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModels()

    lateinit var bestBakeryAdapter: BestBakeryAdapter
    lateinit var bestReviewAdapter: BestReviewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        bestBakeryAdapter = BestBakeryAdapter()
        binding.rvHomeBestBakeryList.adapter = bestBakeryAdapter
        bestBakeryAdapter.submitList(viewModel.mockBestBakeryList)

        bestReviewAdapter = BestReviewAdapter()
        binding.rvHomeBestReviewList.adapter = bestReviewAdapter
        bestReviewAdapter.submitList(viewModel.mockBestReviewList)
    }

    private fun addListeners() {
        binding.tvHomeSearch.setOnClickListener {
            moveToSearch()
        }
    }

    private fun moveToSearch() {
        startActivity(Intent(requireContext(), SearchActivity::class.java))
    }
}
