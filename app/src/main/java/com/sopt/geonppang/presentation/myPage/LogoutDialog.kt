package com.sopt.geonppang.presentation.myPage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.geonppang.R
import com.sopt.geonppang.data.service.KakaoAuthService
import com.sopt.geonppang.databinding.DialogMiddleBinding
import com.sopt.geonppang.presentation.auth.SignActivity
import com.sopt.geonppang.presentation.type.DialogType
import com.sopt.geonppang.presentation.type.PlatformType
import com.sopt.geonppang.util.UiState
import com.sopt.geonppang.util.binding.BindingDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class LogoutDialog : BindingDialogFragment<DialogMiddleBinding>(R.layout.dialog_middle) {
    @Inject
    lateinit var kakaoSignService: KakaoAuthService

    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this.viewLifecycleOwner

        initLayout()
        addListeners()
        collectData()
    }

    private fun initLayout() {
        binding.dialogType = DialogType.LOGOUT
    }

    private fun addListeners() {
        binding.btnDialogNo.setOnClickListener {
            dismiss()
        }

        binding.btnDialogYes.setOnClickListener {
            when (viewModel.platformType) {
                PlatformType.NONE.name -> {
                    viewModel.logout()
                }

                PlatformType.KAKAO.name -> {
                    kakaoSignService.logoutKakao(viewModel::logout)
                }
            }
        }
    }

    private fun collectData() {
        viewModel.logoutState.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    moveToSign()
                    dismiss()
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun moveToSign() {
        val intent = Intent(requireContext(), SignActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
