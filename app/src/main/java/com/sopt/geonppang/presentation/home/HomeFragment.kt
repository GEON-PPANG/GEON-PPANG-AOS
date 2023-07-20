package com.sopt.geonppang.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.FragmentHomeBinding
import com.sopt.geonppang.presentation.detail.DetailActivity
import com.sopt.geonppang.presentation.filter.FilterActivity
import com.sopt.geonppang.presentation.search.SearchActivity
import com.sopt.geonppang.presentation.type.FilterInfoType
import com.sopt.geonppang.util.UiState
import com.sopt.geonppang.util.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModels()

    lateinit var bestBakeryAdapter: BestBakeryAdapter
    lateinit var bestReviewAdapter: BestReviewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        addListeners()
        collectData()
    }

    private fun initLayout() {
        bestBakeryAdapter = BestBakeryAdapter(::moveToDetail)
        binding.rvHomeBestBakeryList.adapter = bestBakeryAdapter

        bestReviewAdapter = BestReviewAdapter(::moveToDetail)
        binding.rvHomeBestReviewList.adapter = bestReviewAdapter
    }

    private fun addListeners() {
        binding.tvHomeSearch.setOnClickListener {
            moveToSearch()
        }

        binding.ivHomeFilter.setOnClickListener {
            moveToFilter()
        }
    }

    private fun collectData() {
        viewModel.bestBakeryListState.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    bestBakeryAdapter.submitList(it.data)
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)

        viewModel.bestReviewListState.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    bestReviewAdapter.submitList(it.data)
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun moveToSearch() {
        startActivity(Intent(requireContext(), SearchActivity::class.java))
    }

    private fun moveToDetail(activityName: String, bakeryId: Int) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra(ACTIVITY_NAME, activityName)
        intent.putExtra(BAKERY_ID, bakeryId)
        startActivity(intent)
    }

    private fun moveToFilter() {
        val intent = Intent(requireContext(), FilterActivity::class.java)
        intent.putExtra(FILTER_INFO, FilterInfoType.HOME.activityName)
        intent.putExtra(MAX_PAGE, FilterInfoType.HOME.maxPage)
        startActivity(intent)
    }

    companion object {
        const val BAKERY_ID = "bakeryId"
        const val ACTIVITY_NAME = "activityName"
        const val FILTER_INFO = "filterInfo"
        const val MAX_PAGE = "maxPage"
    }
}
