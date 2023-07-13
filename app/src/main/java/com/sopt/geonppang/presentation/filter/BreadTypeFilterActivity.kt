package com.sopt.geonppang.presentation.filter

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityBreadTypeFilterBinding
import com.sopt.geonppang.domain.model.BreadType
import com.sopt.geonppang.presentation.type.BreadFilterType
import com.sopt.geonppang.util.binding.BindingActivity
import timber.log.Timber

class BreadTypeFilterActivity :
    BindingActivity<ActivityBreadTypeFilterBinding>(R.layout.activity_bread_type_filter) {
    private val viewModel by viewModels<FilterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.setIsNextBtnEnabled(false)

        addListeners()
    }

    private fun addListeners() {
        binding.ivBreadTypeFilterArrowLeft.setOnClickListener {
            finish()
        }

        binding.btnBreadTypeFilterNext.setOnClickListener {
            val userType = with(viewModel.breadFilterType.value) {
                BreadType(
                    this?.get(BreadFilterType.GLUTENFREE) == true,
                    this?.get(BreadFilterType.VEGAN) == true,
                    this?.get(BreadFilterType.NUTFREE) == true,
                    this?.get(BreadFilterType.SUGARFREE) == true
                )
            }
            moveToNutrientTypeFilter()
            Timber.d(userType.toString())
        }
    }

    private fun moveToNutrientTypeFilter() {
        val intent = Intent(this, NutrientTypeFilterActivity::class.java)
        startActivity(intent)
    }
}
