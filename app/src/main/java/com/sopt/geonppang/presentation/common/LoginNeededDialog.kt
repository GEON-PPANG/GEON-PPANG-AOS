package com.sopt.geonppang.presentation.common

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.data.datasource.local.GPDataSource
import com.sopt.geonppang.data.service.KakaoAuthService
import com.sopt.geonppang.databinding.DialogBottomLoginNeededBinding
import com.sopt.geonppang.presentation.auth.AuthViewModel
import com.sopt.geonppang.presentation.auth.SignUpActivity
import com.sopt.geonppang.presentation.login.LoginActivity
import com.sopt.geonppang.presentation.type.LoginNeededType
import com.sopt.geonppang.presentation.type.PlatformType
import com.sopt.geonppang.util.binding.BindingBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginNeededDialog(
    private val loginNeededType: LoginNeededType
) :
    BindingBottomSheetDialogFragment<DialogBottomLoginNeededBinding>(R.layout.dialog_bottom_login_needed) {

    @Inject
    lateinit var kakaoSignService: KakaoAuthService
    private lateinit var gpDataSource: GPDataSource
    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner

        initLayout()
        addListener()
    }

    private fun addListener() {
        binding.ivDismiss.setOnClickListener { dismiss() }

        binding.btnStartKakao.setOnClickListener {
            kakaoSignService.startKakaoLogin(viewModel::signUp)
        }

        binding.tvSignupWithEmail.setOnClickListener {
            gpDataSource = GPDataSource(it.context)
            gpDataSource.platformType = PlatformType.NONE.name
            moveToSignUp()
        }

        binding.tvLoginWithEmail.setOnClickListener {
            moveToLogIn()
        }
    }

    private fun initLayout() {
        binding.loginNeedType = loginNeededType
    }

    private fun moveToSignUp() {
        val intent = Intent(requireContext(), SignUpActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun moveToLogIn() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
