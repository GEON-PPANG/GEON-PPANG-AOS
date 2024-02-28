package com.sopt.geonppang.presentation.bakeryList

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.ChipGroup
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.FragmentBakeryListBinding
import com.sopt.geonppang.presentation.common.LoginNeededDialog
import com.sopt.geonppang.presentation.detail.DetailActivity
import com.sopt.geonppang.presentation.detail.DetailActivity.Companion.SOURCE
import com.sopt.geonppang.presentation.detail.DetailActivity.Companion.VIEW_DETAIL_PAGE_AT
import com.sopt.geonppang.presentation.filterSetting.FilterSettingActivity
import com.sopt.geonppang.presentation.search.SearchActivity
import com.sopt.geonppang.presentation.type.BakeryCategoryType
import com.sopt.geonppang.presentation.type.BakerySortType
import com.sopt.geonppang.presentation.type.BreadFilterType
import com.sopt.geonppang.presentation.type.FilterInfoType
import com.sopt.geonppang.presentation.type.LoginNeededType
import com.sopt.geonppang.presentation.type.UserRoleType
import com.sopt.geonppang.util.AmplitudeUtils
import com.sopt.geonppang.util.CustomItemDecoration
import com.sopt.geonppang.util.binding.BindingFragment
import com.sopt.geonppang.util.extension.breadTypeListToChips
import com.sopt.geonppang.util.extension.toBreadTypePointM2Chip
import com.sopt.geonppang.util.setVisibility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BakeryListFragment :
    BindingFragment<FragmentBakeryListBinding>(R.layout.fragment_bakery_list),
    BakerySortTypeListener {
    private val bakeryListViewModel: BakeryListViewModel by viewModels()
    private lateinit var bakeryAdapter: BakeryListPagingDataAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = bakeryListViewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        initLayout()
        addListeners()
        collectData()
    }

    private fun initLayout() {
        bakeryAdapter = BakeryListPagingDataAdapter(::moveToDetail, ::initBreadTypeChips)
        binding.rvBakeryList.apply {
            adapter = bakeryAdapter
            addItemDecoration(CustomItemDecoration(requireContext()))
        }

        val isFilterUnSelectedMember =
            bakeryListViewModel.userRoleType.value == UserRoleType.FILTER_UNSELECTED_MEMBER.name

        with(binding) {
            includeHomeSpeechBubble.root.setVisibility(isFilterUnSelectedMember)
            layoutBakeryListMyFilterApply.isEnabled = !isFilterUnSelectedMember
        }
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

        // 비회원, 회원 분기처리
        binding.ivBakeryListFilter.setOnClickListener {
            val isNoneMember =
                bakeryListViewModel.userRoleType.value == UserRoleType.NONE_MEMBER.name

            if (isNoneMember) {
                showLoginNeedDialog()
            } else {
                moveToFilter()
                AmplitudeUtils.trackEvent(START_FILTER_LIST)
            }
        }

        binding.layoutBakeryListMyFilterApply.setOnClickListener {
            onApplyPersonalFilterClicked()
        }
    }

    private fun collectData() {
        bakeryListViewModel.bakeryListFilterType.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                lifecycleScope.launch {
                    bakeryListViewModel.fetchBakeryListPagingData().collectLatest {
                        bakeryAdapter.submitData(it)
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        bakeryListViewModel.bakeryListFilterType
            .map { it.isPersonalFilterApplied }
            .distinctUntilChanged()
            .flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach { isPersonalFilterApplied ->
                if (!isPersonalFilterApplied) {
                    AmplitudeUtils.trackEvent(CLICK_PERSONAL_FILTER_APPLY_OFF)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        bakeryListViewModel.bakeryListFilterType
            .map {
                listOfNotNull(
                    if (it.isHard) BakeryCategoryType.HARD.titleString else null,
                    if (it.isDessert) BakeryCategoryType.DESSERT.titleString else null,
                    if (it.isBrunch) BakeryCategoryType.BRUNCH.titleString else null,
                )
            }
            .distinctUntilChanged()
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { categoryList ->
                if (categoryList.isNotEmpty()) {
                    AmplitudeUtils.trackEventWithProperties(
                        CLICK_CATEGORY,
                        CATEGORY,
                        categoryList
                    )
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initBreadTypeChips(chipGroup: ChipGroup, breadFilterList: List<BreadFilterType>) {
        if (breadFilterList.isNotEmpty()) {
            chipGroup.breadTypeListToChips(
                breadTypeList = breadFilterList,
                toChip = {
                    this.toBreadTypePointM2Chip(layoutInflater)
                }
            )
        }
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
            putString(
                "bakerySortType",
                bakeryListViewModel.bakeryListFilterType.value.sortType.sortType
            )
        }
        bakeryListSortBottomSheetDialog.setDataListener(this)
        bakeryListSortBottomSheetDialog.show(parentFragmentManager, "bakeryListSortDialog")
    }

    private fun showLoginNeedDialog() {
        LoginNeededDialog(LoginNeededType.LOGIN_NEEDED_FILTER).show(
            parentFragmentManager,
            LOGIN_NEEDED
        )
    }

    // BakeryListDialog에서 부터 받아온 데이터를 처리
    override fun onBakerySortTypeSelected(bakerySortType: BakerySortType) {
        bakeryListViewModel.setBakerySortType(bakerySortType)
    }

    private fun onApplyPersonalFilterClicked() {
        val isNoneMember =
            bakeryListViewModel.userRoleType.value == UserRoleType.NONE_MEMBER.name

        if (isNoneMember) {
            showLoginNeedDialog()
        } else {
            bakeryListViewModel.setIsPersonalFilterAppliedState()
        }
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
        const val LOGIN_NEEDED = "loginNeeded"
    }
}
