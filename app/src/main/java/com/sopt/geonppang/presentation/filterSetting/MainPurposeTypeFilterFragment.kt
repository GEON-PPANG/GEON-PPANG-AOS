package com.sopt.geonppang.presentation.filterSetting

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.FragmentMainPurposeTypeFilterBinding
import com.sopt.geonppang.presentation.MainActivity
import com.sopt.geonppang.util.AmplitudeUtils
import com.sopt.geonppang.util.binding.BindingFragment
import com.sopt.geonppang.util.extension.setOnSingleClickListener

class MainPurposeTypeFilterFragment :
    BindingFragment<FragmentMainPurposeTypeFilterBinding>(R.layout.fragment_main_purpose_type_filter) {
    private val viewModel: FilterSettingViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        addListeners()
    }

    private fun addListeners() {
        binding.layoutMainPurposeTypeFilterSkip.setOnClickListener {
            moveToMain()
        }

        binding.tvMainPurposeTypeFilterSkip.setOnSingleClickListener {
            AmplitudeUtils.trackEvent(CLICK_SKIP)
        }
    }

    private fun moveToMain() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    companion object {
        const val CLICK_SKIP = "click_skip"
    }
}
