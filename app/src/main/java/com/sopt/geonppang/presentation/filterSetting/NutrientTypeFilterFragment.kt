package com.sopt.geonppang.presentation.filterSetting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.FragmentNutrientTypeFilterBinding
import com.sopt.geonppang.util.binding.BindingFragment

class NutrientTypeFilterFragment :
    BindingFragment<FragmentNutrientTypeFilterBinding>(R.layout.fragment_nutrient_type_filter) {
    private val viewModel: FilterSettingViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner
    }
}
