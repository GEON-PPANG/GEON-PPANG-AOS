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
import com.sopt.geonppang.presentation.filterSetting.FilterSettingActivity
import com.sopt.geonppang.presentation.search.SearchActivity
import com.sopt.geonppang.presentation.type.FilterInfoType
import com.sopt.geonppang.util.AmplitudeUtils
import com.sopt.geonppang.util.UiState
import com.sopt.geonppang.util.binding.BindingFragment
import com.sopt.geonppang.util.setVisibility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var bestBakeryAdapter: BestBakeryAdapter
    private lateinit var bestReviewAdapter: BestReviewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

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
            AmplitudeUtils.trackEvent(CLICK_SEARCH_HOME)
            moveToSearch()
        }

        binding.ivHomeFilter.setOnClickListener {
            AmplitudeUtils.trackEvent(START_FILTER_HOME)
            moveToFilter()
        }

        binding.includeHomeSpeechBubble.ivSpeechBubbleClose.setOnClickListener {
            binding.includeHomeSpeechBubble.root.setVisibility(false)
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

        viewModel.isFilterSelected.flowWithLifecycle(lifecycle).onEach { isFilterSelected ->
            binding.tvHomeBestBakeryTitle1.setVisibility(isFilterSelected)
            binding.tvHomeBestReviewTitle1.setVisibility(isFilterSelected)
            binding.includeHomeSpeechBubble.root.setVisibility(!isFilterSelected)
        }.launchIn(lifecycleScope)
    }

    private fun moveToSearch() {
        val intent = Intent(requireContext(), SearchActivity::class.java)
        intent.putExtra(VIEW_TO_VIEW, HOME_TO_SEARCH)
        startActivity(intent)
    }

    private fun moveToDetail(activityName: String, bakeryId: Int) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra(ACTIVITY_NAME, activityName)
        intent.putExtra(BAKERY_ID, bakeryId)
        startActivity(intent)
    }

    private fun moveToFilter() {
        val intent = Intent(requireContext(), FilterSettingActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        intent.putExtra(FILTER_INFO, FilterInfoType.HOME.name)
        startActivity(intent)
    }

    companion object {
        const val BAKERY_ID = "bakeryId"
        const val ACTIVITY_NAME = "activityName"
        const val FILTER_INFO = "filterInfo"
        const val VIEW_TO_VIEW = "viewToView"
        const val HOME_TO_SEARCH = "homeToSearch"
        const val CLICK_SEARCH_HOME = "click_search_home"
        const val START_FILTER_HOME = "start_filter_home"
    }
}
