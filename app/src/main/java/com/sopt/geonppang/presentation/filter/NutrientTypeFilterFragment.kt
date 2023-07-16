package com.sopt.geonppang.presentation.filter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.FragmentNutrientTypeFilterBinding
import com.sopt.geonppang.util.binding.BindingFragment

class NutrientTypeFilterFragment :
    BindingFragment<FragmentNutrientTypeFilterBinding>(R.layout.fragment_nutrient_type_filter) {
    private lateinit var viewModel: FilterViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(FilterViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        addListeners()
    }

    private fun addListeners() {
        val fragmentManager = requireActivity().supportFragmentManager

        binding.ivNutrientTypeFilterArrowLeft.setOnClickListener {
            fragmentManager.popBackStack()
        }

        binding.btnNutrientTypeFilterNext.setOnClickListener {
            fragmentManager.commit {
                replace(
                    R.id.fcv_filter_container,
                    StartGeonPpangFragment()
                )
            }
        }
    }
}
