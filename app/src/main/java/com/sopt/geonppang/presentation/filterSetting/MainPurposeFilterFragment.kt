package com.sopt.geonppang.presentation.filterSetting

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.data.datasource.local.GPDataSource
import com.sopt.geonppang.databinding.FragmentMainPurposeFilterBinding
import com.sopt.geonppang.presentation.MainActivity
import com.sopt.geonppang.presentation.auth.SignActivity
import com.sopt.geonppang.presentation.type.PlatformType
import com.sopt.geonppang.util.binding.BindingFragment

class MainPurposeFilterFragment :
    BindingFragment<FragmentMainPurposeFilterBinding>(R.layout.fragment_main_purpose_filter) {
    private val viewModel: FilterSettingViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        addListeners()
    }

    private fun addListeners() {
        binding.layoutMainPurposeSkip.setOnClickListener {
            val gpDataSource = GPDataSource(requireContext())
            when (gpDataSource.platformType) {
                PlatformType.NONE.name -> {
                    moveToSign()
                }

                PlatformType.KAKAO.name -> {
                    moveToMain()
                }
            }
        }
    }

    private fun moveToMain() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun moveToSign() {
        val intent = Intent(requireContext(), SignActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
