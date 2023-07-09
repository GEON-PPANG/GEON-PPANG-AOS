package com.sopt.geonppang.presentation.filter

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityNutrientTypeFilterBinding
import com.sopt.geonppang.util.binding.BindingActivity

class NutrientTypeFilterActivity :
    BindingActivity<ActivityNutrientTypeFilterBinding>(R.layout.activity_nutrient_type_filter) {
    private val viewModel by viewModels<FilterViewModel>()
    private lateinit var adapter: NutrientTypeFilterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.setIsNextBtnEnabled(false)

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        adapter = NutrientTypeFilterAdapter(this, viewModel)
        adapter.submitList(viewModel.nutrientTypeFilterList)

        binding.rvNutrientTypeFilterOption.adapter = adapter
    }

    private fun addListeners() {
        binding.btnNutrientTypeFilterNext.setOnClickListener {
            val intent = Intent(this, StartGeonPpangActivity::class.java)
            startActivity(intent)
        }
    }
}
