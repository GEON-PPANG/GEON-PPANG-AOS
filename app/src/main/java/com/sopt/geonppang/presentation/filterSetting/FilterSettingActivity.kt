package com.sopt.geonppang.presentation.filterSetting

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityFilterBinding
import com.sopt.geonppang.presentation.MainActivity
import com.sopt.geonppang.presentation.type.FilterInfoType
import com.sopt.geonppang.util.AmplitudeUtils
import com.sopt.geonppang.util.binding.BindingActivity
import com.sopt.geonppang.util.setInvisibility
import dagger.hilt.android.AndroidEntryPoint

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
        addObservers()
    }

    private fun initLayout() {
        adapter = FilterViewPagerAdapter(this)

        binding.vpFilterContainer.adapter = adapter
        binding.vpFilterContainer.isUserInputEnabled = false
        binding.vpFilterContainer.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    viewModel.setCurrentItem(position)
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

        binding.btnFilterNext.setOnClickListener {
            when (binding.vpFilterContainer.currentItem) {
                2 -> {
                    viewModel.setUserFilter()

                    when (viewModel.previousActivity.value) {
                        FilterInfoType.BAKERYLIST -> {
                            AmplitudeUtils.trackEvent(COMPLETE_FILTER_LIST)
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            intent.putExtra(BAKERY_LIST_FRAGMENT, BAKERY_LIST_FRAGMENT)
                            startActivity(intent)
                        }

                        FilterInfoType.MYPAGE -> {
                            AmplitudeUtils.trackEvent(COMPLETE_FILTER_MY)
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            intent.putExtra(MYPAGE_FRAGMENT, MYPAGE_FRAGMENT)
                            startActivity(intent)
                        }

                        FilterInfoType.HOME -> {
                            AmplitudeUtils.trackEvent(COMPLETE_FILTER_HOME)
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)
                        }

                        else -> {
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                    }
                }

                else -> {
                    if (binding.vpFilterContainer.currentItem == 1) {
                        viewModel.setIsLastPage(true)
                    }

                    binding.vpFilterContainer.currentItem++
                }
            }
        }
    }

    private fun addObservers() {
        viewModel.mainPurpose.observe(this) { mainPurposeType ->
            binding.btnFilterNext.isEnabled = mainPurposeType != null
        }

        viewModel.isUserBreadTypeSelected.observe(this) { isUserBreadTypeSelected ->
            binding.btnFilterNext.isEnabled = isUserBreadTypeSelected
        }

        viewModel.isUserNutrientFilterTypeSelected.observe(this) { isUserNutrientFilterTypeSelected ->
            binding.btnFilterNext.isEnabled = isUserNutrientFilterTypeSelected
        }

        viewModel.currentItem.observe(this) { currentItem ->
            binding.tvFilterPageNumber.text = setPageText(currentItem + 1)

            when (currentItem) {
                0 -> {
                    if (viewModel.previousActivity.value == FilterInfoType.ONBOARDING) {
                        binding.ivFilterArrowLeft.setInvisibility(false)
                    }
                }

                else -> {
                    binding.ivFilterArrowLeft.setInvisibility(true)
                }
            }
        }

        viewModel.currentItemFilterSelected.observe(this) { currentItemFilterSelected ->
            binding.btnFilterNext.isEnabled = currentItemFilterSelected
        }
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

            FilterInfoType.BAKERYLIST -> {
                AmplitudeUtils.trackEvent(CLICK_FILTER_BACK_LIST)
            }

            FilterInfoType.MYPAGE -> {
                AmplitudeUtils.trackEvent(CLICK_FILTER_BACK_MY)
            }

            else -> {}
        }
    }

    companion object {
        const val FILTER_INFO = "filterInfo"
        const val MYPAGE_FRAGMENT = "MyPageFragment"
        const val BAKERY_LIST_FRAGMENT = "BakeryListFragment"
        const val PAGE_FORMAT = "%d/3"
        const val CLICK_FILTER_BACK_HOME = "click_filter_back_home"
        const val CLICK_FILTER_BACK_LIST = "click_filterback_list"
        const val CLICK_FILTER_BACK_MY = "click_filter_back_mypage"
        const val COMPLETE_FILTER_HOME = "complete_filter_home"
        const val COMPLETE_FILTER_LIST = "complete_filter_list"
        const val COMPLETE_FILTER_MY = "complete_filter_mypage"
    }
}
