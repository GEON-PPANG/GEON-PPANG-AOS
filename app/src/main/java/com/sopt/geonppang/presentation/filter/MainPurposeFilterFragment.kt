package com.sopt.geonppang.presentation.filter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.FragmentMainPurposeFilterBinding
import com.sopt.geonppang.util.binding.BindingFragment

class MainPurposeFilterFragment :
    BindingFragment<FragmentMainPurposeFilterBinding>(R.layout.fragment_main_purpose_filter) {
    private val viewModel: FilterViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}
