package com.sopt.geonppang.presentation.bakeryList

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.FragmentBakeryListBinding
import com.sopt.geonppang.presentation.detail.DetailActivity
import com.sopt.geonppang.presentation.detail.DetailActivity.Companion.SOURCE
import com.sopt.geonppang.presentation.detail.DetailActivity.Companion.VIEW_DETAIL_PAGE_AT
import com.sopt.geonppang.presentation.filterSetting.FilterSettingActivity
import com.sopt.geonppang.presentation.search.SearchActivity
import com.sopt.geonppang.presentation.type.BakerySortType
import com.sopt.geonppang.presentation.type.FilterInfoType
import com.sopt.geonppang.util.AmplitudeUtils
import com.sopt.geonppang.util.UiState
import com.sopt.geonppang.util.binding.BindingFragment
import com.sopt.geonppang.util.setVisibility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class BakeryListFragment :
    BindingFragment<FragmentBakeryListBinding>(R.layout.fragment_bakery_list),
    BakerySortTypeListener {
    private val viewModel: BakeryListViewModel by viewModels()

    lateinit var bakeryAdapter: BakeryListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        initLayout()
        addListeners()
        collectData()
    }

    private fun initLayout() {
        bakeryAdapter = BakeryListAdapter(::moveToDetail)
        binding.rvBakeryList.adapter = bakeryAdapter
    }

    private fun addListeners() {
        binding.layoutBakeryListSortFilter.setOnClickListener {
            showBakeryListSortDialog()
        }

        binding.ivSearch.setOnClickListener {
            AmplitudeUtils.trackEvent(CLICK_SEARCH_LIST)
            moveToSearch()
        }

        binding.ivBakeryListFilter.setOnClickListener {
            AmplitudeUtils.trackEvent(START_FILTER_LIST)
            moveToFilter()
        }
        binding.includeHomeSpeechBubble.ivSpeechBubbleClose.setOnClickListener {
            binding.includeHomeSpeechBubble.root.visibility = View.INVISIBLE
        }
    }

    private fun collectData() {
        viewModel.bakeryListState.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    bakeryAdapter.setBakeryList(it.data.toMutableList())
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)
        viewModel.isPersonalFilterApplied.flowWithLifecycle(lifecycle)
            .onEach { isPersonalFilterApplied ->
                if (viewModel.isFilterSelected.value && isPersonalFilterApplied == false)
                    AmplitudeUtils.trackEvent(CLICK_PERSONAL_FILTER_APPLY_OFF)
                viewModel.fetchBakeryList()
            }.launchIn(lifecycleScope)
        viewModel.bakeryCategoryType.flowWithLifecycle(lifecycle).onEach { bakeryCategoryType ->
            val selectedCategory = bakeryCategoryType.entries.filter { it.value }.map { it.key }
            if (selectedCategory.isNotEmpty()) {
                AmplitudeUtils.trackEventWithProperties(
                    CLICK_CATEGORY,
                    CATEGORY,
                    selectedCategory
                )
            }
            viewModel.fetchBakeryList()
        }.launchIn(lifecycleScope)
        viewModel.isFilterSelected.flowWithLifecycle(lifecycle).onEach { isFilterSelected ->
            binding.includeHomeSpeechBubble.root.setVisibility(!isFilterSelected)
            binding.checkBakeryListMyFilter.isEnabled = isFilterSelected
        }.launchIn(lifecycleScope)
    }

    private fun moveToDetail(bakeryId: Int) {
        AmplitudeUtils.trackEventWithProperties(VIEW_DETAIL_PAGE_AT, SOURCE, LIST)
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra(BAKERY_ID, bakeryId)
        startActivity(intent)
    }

    private fun moveToSearch() {
        val intent = Intent(requireActivity(), SearchActivity::class.java)
        intent.putExtra(VIEW_TO_VIEW, BAKERY_LIST_TO_SEARCH)
        startActivity(intent)
    }

    private fun moveToFilter() {
        val intent = Intent(requireContext(), FilterSettingActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        intent.putExtra(FILTER_INFO, FilterInfoType.BAKERY_LIST.name)
        startActivity(intent)
    }

    private fun showBakeryListSortDialog() {
        val bakeryListSortBottomSheetDialog = BakeryListSortBottomSheetDialog()

        // BakeryListSortDialog로 현재 정렬 정보 전달
        bakeryListSortBottomSheetDialog.arguments = Bundle().apply {
            putString("bakerySortType", viewModel.bakerySort.value?.sortType)
        }
        bakeryListSortBottomSheetDialog.setDataListener(this)
        bakeryListSortBottomSheetDialog.show(parentFragmentManager, "bakeryListSortDialog")
    }

    // BakeryListDialog에서 부터 받아온 데이터를 처리
    override fun onBakerySortTypeSelected(bakerySortType: BakerySortType) {
        viewModel.setBakerySortType(bakerySortType)
        viewModel.fetchBakeryList()
    }

    companion object {
        const val BAKERY_ID = "bakeryId"
        const val FILTER_INFO = "filterInfo"
        const val VIEW_TO_VIEW = "viewToView"
        const val BAKERY_LIST_TO_SEARCH = "bakeryListToSearch"
        const val CLICK_SEARCH_LIST = "click_search_list"
        const val START_FILTER_LIST = "start_filter_list"
        const val CLICK_PERSONAL_FILTER_APPLY_OFF = "click_filteroff"
        const val CLICK_CATEGORY = "click_category"
        const val CATEGORY = "category"
        const val LIST = "LIST"
    }
}
