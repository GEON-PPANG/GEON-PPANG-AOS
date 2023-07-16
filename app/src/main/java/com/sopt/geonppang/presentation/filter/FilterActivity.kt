package com.sopt.geonppang.presentation.filter

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityFilterBinding
import com.sopt.geonppang.util.binding.BindingActivity

class FilterActivity : BindingActivity<ActivityFilterBinding>(R.layout.activity_filter) {
    private lateinit var viewModel: FilterViewModel
    private lateinit var adapter: FilterViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(FilterViewModel::class.java)

        initLayout()
        addListeners()
        addObservers()
    }

    private fun initLayout() {
        adapter = FilterViewPagerAdapter(this)
        binding.vpFilterContainer.adapter = adapter
    }

    private fun addListeners() {
        binding.btnFilterNext.setOnClickListener {
            when (binding.vpFilterContainer.currentItem) {
                2 -> {
                    startActivity(Intent(this, WelcomeActivity::class.java))
                }

                else -> {
                    binding.vpFilterContainer.currentItem++
                    binding.btnFilterNext.isEnabled = false
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
}
