package com.sopt.geonppang.presentation.filterSetting

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityFilterBinding
import com.sopt.geonppang.presentation.MainActivity
import com.sopt.geonppang.presentation.model.AmplitudeFilterSettingInfo
import com.sopt.geonppang.presentation.type.BreadFilterType
import com.sopt.geonppang.presentation.type.FilterInfoType
import com.sopt.geonppang.presentation.type.NutrientFilterType
import com.sopt.geonppang.util.AmplitudeUtils
import com.sopt.geonppang.util.UiState
import com.sopt.geonppang.util.binding.BindingActivity
import com.sopt.geonppang.util.extension.setOnSingleClickListener
import com.sopt.geonppang.util.setInvisibility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class FilterSettingActivity : BindingActivity<ActivityFilterBinding>(R.layout.activity_filter) {
    private val viewModel: FilterSettingViewModel by viewModels()
    private lateinit var adapter: FilterViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initLayout()
        addListeners()
        collectData()
    }

    private fun initLayout() {
        adapter = FilterViewPagerAdapter(this)

        binding.vpFilterContainer.adapter = adapter
        binding.vpFilterContainer.isUserInputEnabled = false
        binding.vpFilterContainer.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    viewModel.setCurrentPage(position)
                }
            })
        setPreviousActivity()
    }

    private fun addListeners() {
        binding.ivFilterArrowLeft.setOnClickListener {
            when (binding.vpFilterContainer.currentItem) {
                0 -> {
                    trackDeviationFromFilterView()
                    finish()
                }

                else -> {
                    binding.vpFilterContainer.currentItem--
                }
            }
        }

        // TODO 로직 수정
        binding.btnFilterNext.setOnSingleClickListener {
            when (binding.vpFilterContainer.currentItem) {
                0 -> {
                    if (viewModel.previousActivity.value == FilterInfoType.ONBOARDING)
                        AmplitudeUtils.trackEvent(FILTER_SETTING_FIRST_PAGE)
                    binding.vpFilterContainer.currentItem++
                }

                1 -> {
                    if (viewModel.previousActivity.value == FilterInfoType.ONBOARDING)
                        AmplitudeUtils.trackEvent(FILTER_SETTING_SECOND_PAGE)
                    binding.vpFilterContainer.currentItem++

                }

                2 -> {
                    viewModel.setUserFilter()
                    when (viewModel.previousActivity.value) {
                        FilterInfoType.ONBOARDING -> {
                            AmplitudeUtils.trackEvent(FILTER_SETTING_LAST_PAGE)
                            AmplitudeUtils.trackEvent(CLICK_FILTER_COMPLETE_ONBOARDING)
                        }

                        FilterInfoType.BAKERY_LIST -> {
                            AmplitudeUtils.trackEvent(CLICK_FILTER_COMPLETE_LIST)
                        }

                        FilterInfoType.MY_PAGE -> {
                            AmplitudeUtils.trackEvent(CLICK_FILTER_COMPLETE_MY)
                        }

                        FilterInfoType.HOME -> {
                            AmplitudeUtils.trackEvent(CLICK_FILTER_COMPLETE_HOME)
                        }

                        else -> {}

                    }

                }
            }
        }
    }

    private fun collectData() {
        viewModel.currentPage.flowWithLifecycle(lifecycle).onEach { currentPage ->
            currentPage?.let {
                binding.tvFilterPageNumber.text = setPageText(it + 1)
            }

            when (currentPage) {
                0 -> {
                    if (viewModel.previousActivity.value == FilterInfoType.ONBOARDING) {
                        binding.ivFilterArrowLeft.setInvisibility(false)
                        AmplitudeUtils.trackEvent(START_FILTER_ONBOARDING)
                    }
                }

                else -> {
                    binding.ivFilterArrowLeft.setInvisibility(true)
                }
            }
        }.launchIn(lifecycleScope)

        viewModel.isFilterBtnEnabled.flowWithLifecycle(lifecycle).onEach { isFilterBtnEnabled ->
            binding.btnFilterNext.isEnabled = isFilterBtnEnabled
        }.launchIn(lifecycleScope)

        viewModel.selectedFilterState.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    when (viewModel.previousActivity.value) {
                        FilterInfoType.BAKERY_LIST -> {
                            completeFilterPropertyEvent(COMPLETE_FILTER_LIST, it)
                            moveToMain(BAKERY_LIST_FRAGMENT)
                        }

                        FilterInfoType.MY_PAGE -> {
                            completeFilterPropertyEvent(COMPLETE_FILTER_MY, it)
                            moveToMain(MY_PAGE_FRAGMENT)
                        }

                        FilterInfoType.HOME -> {
                            completeFilterPropertyEvent(COMPLETE_FILTER_HOME, it)
                            moveToMain(null)
                        }

                        FilterInfoType.ONBOARDING -> {
                            completeFilterPropertyEvent(COMPLETE_FILTER_ONBOARDING, it)
                            moveOnBoardingToMain()
                        }

                        else -> {}
                    }
                }

                else -> {
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun moveOnBoardingToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun moveToMain(initialFragment: String?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        if (!initialFragment.isNullOrEmpty()) {
            intent.putExtra(initialFragment, initialFragment)
        }
        startActivity(intent)
    }

    private fun setPreviousActivity() {
        intent.getStringExtra(FILTER_INFO)?.let { filterInfoType ->
            viewModel.setPreviousActivity(FilterInfoType.valueOf(filterInfoType))
        }
    }

    private fun setPageText(currentPage: Int): String {
        return String.format(PAGE_FORMAT, currentPage)
    }

    private fun completeFilterPropertyEvent(
        eventName: String,
        property: UiState.Success<AmplitudeFilterSettingInfo>
    ) {
        property.data.mainPurposeType?.let { mainPurposeType ->
            AmplitudeUtils.trackEventWithMapProperties(
                eventName,
                mapOf(
                    MAIN_PURPOSE to mainPurposeType,
                    BREAD_TYPE to getStringBreadType(property.data.breadType),
                    INGREDIENTS_TYPE to getStringIngredientType(property.data.ingredientType)
                )
            )
        }
    }

    private fun trackDeviationFromFilterView() {
        when (viewModel.previousActivity.value) {
            FilterInfoType.HOME -> {
                AmplitudeUtils.trackEvent(CLICK_FILTER_BACK_HOME)
            }

            FilterInfoType.BAKERY_LIST -> {
                AmplitudeUtils.trackEvent(CLICK_FILTER_BACK_LIST)
            }

            FilterInfoType.MY_PAGE -> {
                AmplitudeUtils.trackEvent(CLICK_FILTER_BACK_MY)
            }

            else -> {}
        }
    }

    private fun getStringBreadType(breadType: Map<BreadFilterType, Boolean>): List<String> {
        return breadType.entries.filter { it.value }.map { it.key.name }
    }

    private fun getStringIngredientType(ingredientType: Map<NutrientFilterType, Boolean>): List<String> {
        return ingredientType.entries.filter { it.value }.map { it.key.name }
    }

    companion object {
        const val FILTER_INFO = "filterInfo"
        const val MY_PAGE_FRAGMENT = "MyPageFragment"
        const val BAKERY_LIST_FRAGMENT = "BakeryListFragment"
        const val PAGE_FORMAT = "%d/3"
        const val CLICK_FILTER_BACK_HOME = "click_filter_back_home"
        const val CLICK_FILTER_BACK_LIST = "click_filterback_list"
        const val CLICK_FILTER_BACK_MY = "click_filter_back_mypage"
        const val COMPLETE_FILTER_HOME = "complete_filter_home"
        const val COMPLETE_FILTER_LIST = "complete_filter_list"
        const val COMPLETE_FILTER_MY = "complete_filter_mypage"
        const val START_FILTER_ONBOARDING = "start_filter_onboarding"
        const val COMPLETE_FILTER_ONBOARDING = "complete_filter_onboarding"
        const val MAIN_PURPOSE = "main purpose"
        const val BREAD_TYPE = "breadtype"
        const val INGREDIENTS_TYPE = "ingredients type"
        const val FILTER_SETTING_FIRST_PAGE = "click_mainpurpose"
        const val FILTER_SETTING_SECOND_PAGE = "click_breadtype"
        const val FILTER_SETTING_LAST_PAGE = "click_ingredients type"
        const val CLICK_FILTER_COMPLETE_ONBOARDING = "click_filter_complete_onboarding"
        const val CLICK_FILTER_COMPLETE_HOME = "click_filter_complete_home"
        const val CLICK_FILTER_COMPLETE_LIST = "click_filter_complete_list"
        const val CLICK_FILTER_COMPLETE_MY = "click_filter_complete_mypage"
    }
}
