package com.sopt.geonppang.presentation.filterSetting

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityFilterBinding
import com.sopt.geonppang.presentation.MainActivity
import com.sopt.geonppang.presentation.type.FilterInfoType
import com.sopt.geonppang.util.binding.BindingActivity
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

        if (binding.vpFilterContainer.currentItem == 0 && viewModel.previousActivity.value == FilterInfoType.ONBOARDING) {
            binding.ivFilterArrowLeft.visibility = View.INVISIBLE
        }
    }

    private fun addListeners() {
        binding.ivFilterArrowLeft.setOnClickListener {
            when (binding.vpFilterContainer.currentItem) {
                0 -> {
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
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra(BAKERY_LIST_FRAGMENT, BAKERY_LIST_FRAGMENT)
                            startActivity(intent)
                        }

                        FilterInfoType.MYPAGE -> {
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra(MYPAGE_FRAGMENT, MYPAGE_FRAGMENT)
                            startActivity(intent)
                        }

                        else -> {
                            startActivity(Intent(this, MainActivity::class.java))
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

    companion object {
        const val FILTER_INFO = "filterInfo"
        const val MYPAGE_FRAGMENT = "MyPageFragment"
        const val BAKERY_LIST_FRAGMENT = "BakeryListFragment"
        const val PAGE_FORMAT = "%d/3"
    }
}
