package com.sopt.geonppang.presentation.filter

import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityMainPurposeFilterBinding
import com.sopt.geonppang.util.binding.BindingActivity

class MainPurposeFilterActivity :
    BindingActivity<ActivityMainPurposeFilterBinding>(R.layout.activity_main_purpose_filter) {
    private val viewModel by viewModels<FilterViewModel>()
    private lateinit var adapter: MainPurposeFilterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initLayout()
    }

    private fun initLayout() {
        adapter = MainPurposeFilterAdapter(this, viewModel)
        adapter.submitList(viewModel.mainPurposeFilterList)

        binding.rvMainPurposeFilterOption.adapter = adapter
    }
}
