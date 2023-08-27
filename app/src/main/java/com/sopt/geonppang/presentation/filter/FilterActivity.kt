package com.sopt.geonppang.presentation.filter

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityFilterBinding
import com.sopt.geonppang.presentation.MainActivity
import com.sopt.geonppang.presentation.type.FilterInfoType
import com.sopt.geonppang.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterActivity : BindingActivity<ActivityFilterBinding>(R.layout.activity_filter) {
    private val viewModel by viewModels<FilterViewModel>()
    private lateinit var adapter: FilterViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initLayout()
        addListeners()
        addObservers()
        setNextActivity()
    }

    private fun initLayout() {
        adapter = FilterViewPagerAdapter(this)
        binding.vpFilterContainer.adapter = adapter
        binding.vpFilterContainer.isUserInputEnabled = false
        binding.tvFilterPageNumber.text = setPageText(PAGE)
    }

    private fun addListeners() {
        binding.ivFilterArrowLeft.setOnClickListener {
            when (binding.vpFilterContainer.currentItem) {
                0 -> {
                    finish()
                }

                else -> {
                    binding.vpFilterContainer.currentItem--
                    binding.btnFilterNext.isEnabled = true
                }
            }
        }

        binding.btnFilterNext.setOnClickListener {
            when (binding.vpFilterContainer.currentItem) {
                2 -> {
                    viewModel.setUserFilter()
                    if (viewModel.nextActivityName.value == FilterInfoType.HOME.activityName) {
                        startActivity(Intent(this, MainActivity::class.java))
                    } else if (viewModel.nextActivityName.value == FilterInfoType.BAKERYLIST.activityName) {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra(BAKERY_LIST_FRAGMENT, BAKERY_LIST_FRAGMENT)
                        startActivity(intent)
                    } else {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra(MYPAGE_FRAGMENT, MYPAGE_FRAGMENT)
                        startActivity(intent)
                    }
                }

                else -> {
                    if (binding.vpFilterContainer.currentItem == 1) {
                        viewModel.setIsLastPage(true)
                    }

                    binding.vpFilterContainer.currentItem++
                    binding.btnFilterNext.isEnabled = false
                    binding.tvFilterPageNumber.text =
                        setPageText(binding.vpFilterContainer.currentItem + 1)
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
    }

    private fun setNextActivity() {
        val filterInfoType = intent.getStringExtra(FILTER_INFO)
        filterInfoType?.let { filterInfoType ->
            viewModel.setNextActivity(filterInfoType)
        }
    }

    private fun setPageText(currentPage: Int): String {
        return String.format(PAGE_FORMAT, currentPage)
    }

    companion object {
        const val FILTER_INFO = "filterInfo"
        const val MYPAGE_FRAGMENT = "MyPageFragment"
        const val BAKERY_LIST_FRAGMENT = "BakeryListFragment"
        const val PAGE = 1
        const val PAGE_FORMAT = "%d/3"
    }
}
