package com.sopt.geonppang.presentation.filter

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityFilterBinding
import com.sopt.geonppang.presentation.MainActivity
import com.sopt.geonppang.presentation.type.FilterInfoType
import com.sopt.geonppang.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterActivity : BindingActivity<ActivityFilterBinding>(R.layout.activity_filter) {
    private lateinit var viewModel: FilterViewModel
    private lateinit var adapter: FilterViewPagerAdapter
    private var maxPage: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(FilterViewModel::class.java)

        initLayout()
        addListeners()
        addObservers()
        setPreviousActivity()
    }

    private fun initLayout() {
        adapter = FilterViewPagerAdapter(this)
        binding.vpFilterContainer.adapter = adapter
        binding.vpFilterContainer.isUserInputEnabled = false

        maxPage = intent.getIntExtra(MAX_PAGE, -1)
        binding.tvFilterPageNumber.text = setPageText(maxPage - 2, maxPage)

        viewModel.setUserNickName()
    }

    private fun addListeners() {
        binding.includeFilterToolbar.ivBack.setOnClickListener {
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
                    if (viewModel.previousActivityName.value == FilterInfoType.HOME.activityName) {
                        startActivity(Intent(this, MainActivity::class.java))
                    } else if (viewModel.previousActivityName.value == FilterInfoType.ONBOARDING.activityName) {
                        moveToWelcomActivity()
                    } else {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra(MYPAGE_FRAGMENT, MYPAGE_FRAGMENT)
                        startActivity(intent)
                    }
                }

                else -> {
                    binding.vpFilterContainer.currentItem++
                    binding.btnFilterNext.isEnabled = false
                    val currentPage = maxPage - 2 + binding.vpFilterContainer.currentItem
                    binding.tvFilterPageNumber.text = setPageText(currentPage, maxPage)
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

    private fun moveToWelcomActivity() {
        val intent = Intent(this, WelcomeActivity::class.java)
        intent.putExtra(NICKNAME, viewModel.nickName.value)
        startActivity(intent)
    }

    private fun setPreviousActivity() {
        val filterInfoType = intent.getStringExtra(FILTER_INFO)
        filterInfoType?.let { filterInfoType ->
            viewModel.setPreviousActivity(filterInfoType)
        }
    }

    private fun setPageText(currentPage: Int, maxPage: Int): String {
        return String.format(PAGE_FORMAT, currentPage, maxPage)
    }

    companion object {
        const val FILTER_INFO = "filterInfo"
        const val MYPAGE_FRAGMENT = "MyPageFragment"
        const val NICKNAME = "nickName"
        const val MAX_PAGE = "maxPage"
        const val PAGE_FORMAT = "%d/%d"
    }
}
