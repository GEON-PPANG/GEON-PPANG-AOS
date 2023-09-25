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
import com.sopt.geonppang.presentation.auth.SignActivity
import com.sopt.geonppang.presentation.type.FilterInfoType
import com.sopt.geonppang.util.AmplitudeUtils
import com.sopt.geonppang.util.UiState
import com.sopt.geonppang.util.binding.BindingActivity
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
        AmplitudeUtils.trackEvent(START_FILTER_ONBOARDING)
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

        binding.btnFilterNext.setOnClickListener {
            when (binding.vpFilterContainer.currentItem) {
                2 -> {
                    viewModel.setUserFilter()
                    AmplitudeUtils.trackEvent(START_FILTER_ONBOARDING)
                }

                else -> {
                    binding.vpFilterContainer.currentItem++
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
                    }
                }

                else -> {
                    binding.ivFilterArrowLeft.setInvisibility(true)
                }
            }
        }.launchIn(lifecycleScope)

        viewModel.isFilterBtnEnabled.flowWithLifecycle(lifecycle)
            .onEach { isFilterBtnEnabled ->
                binding.btnFilterNext.isEnabled = isFilterBtnEnabled
            }.launchIn(lifecycleScope)

        viewModel.selectedFilterState.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    when (viewModel.previousActivity.value) {
                        FilterInfoType.BAKERY_LIST -> {
                            AmplitudeUtils.trackEvent(COMPLETE_FILTER_LIST)
                            moveToMain(BAKERY_LIST_FRAGMENT)
                        }

                        FilterInfoType.MY_PAGE -> {
                            AmplitudeUtils.trackEvent(COMPLETE_FILTER_MY)
                            moveToMain(MY_PAGE_FRAGMENT)
                        }

                        FilterInfoType.HOME -> {
                            AmplitudeUtils.trackEvent(COMPLETE_FILTER_HOME)
                            moveToMain(null)
                        }

                        FilterInfoType.ONBOARDING -> {
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
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
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
    }
}
