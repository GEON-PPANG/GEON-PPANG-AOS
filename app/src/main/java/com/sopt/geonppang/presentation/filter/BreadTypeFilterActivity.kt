package com.sopt.geonppang.presentation.filter

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityBreadTypeFilterBinding
import com.sopt.geonppang.util.binding.BindingActivity

class BreadTypeFilterActivity :
    BindingActivity<ActivityBreadTypeFilterBinding>(R.layout.activity_bread_type_filter) {
    private val viewModel by viewModels<FilterViewModel>()
    private lateinit var adapter: BreadTypeFilterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.setIsNextBtnEnabled(false)

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        adapter = BreadTypeFilterAdapter(this, viewModel)
        adapter.submitList(viewModel.breadTypeFilterList)

        binding.rvBreadTypeFilterOption.adapter = adapter
    }

    private fun addListeners() {
        binding.btnBreadTypeFilterNext.setOnClickListener {
            val intent = Intent(this, NutrientTypeFilterActivity::class.java)
            startActivity(intent)
        }
    }
}
