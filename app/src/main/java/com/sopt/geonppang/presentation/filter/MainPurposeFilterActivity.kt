package com.sopt.geonppang.presentation.filter

import android.content.Intent
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
        addListeners()
    }

    private fun initLayout() {
        adapter = MainPurposeFilterAdapter(this, viewModel)
        adapter.submitList(viewModel.mainPurposeFilterList)

        binding.rvMainPurposeFilterOption.adapter = adapter
    }

    private fun addListeners() {
        binding.ivMainPurposeFilterArrowLeft.setOnClickListener {
            finish()
        }

        binding.btnMainPurposeFilterNext.setOnClickListener {
            val intent = Intent(this, BreadTypeFilterActivity::class.java)
            startActivity(intent)
        }
    }
}
