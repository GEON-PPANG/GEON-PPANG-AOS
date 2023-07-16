package com.sopt.geonppang.presentation.filter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.FragmentBreadTypeFilterBinding
import com.sopt.geonppang.util.binding.BindingFragment

class BreadTypeFilterFragment :
    BindingFragment<FragmentBreadTypeFilterBinding>(R.layout.fragment_bread_type_filter) {
    private val viewModel: FilterViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}
