package com.sopt.geonppang.presentation.common

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.geonppang.R
import com.sopt.geonppang.data.datasource.local.GPDataSource
import com.sopt.geonppang.data.service.KakaoAuthService
import com.sopt.geonppang.databinding.DialogBottomLoginNeededBinding
import com.sopt.geonppang.presentation.MainActivity
import com.sopt.geonppang.presentation.auth.AuthViewModel
import com.sopt.geonppang.presentation.auth.SignActivity
import com.sopt.geonppang.presentation.auth.SignUpActivity
import com.sopt.geonppang.presentation.auth.SignUpNicknameActivity
import com.sopt.geonppang.presentation.filterSetting.FilterSettingViewModel
import com.sopt.geonppang.presentation.login.LoginActivity
import com.sopt.geonppang.presentation.type.AuthRoleType
import com.sopt.geonppang.presentation.type.LoginNeededType
import com.sopt.geonppang.presentation.type.PlatformType
import com.sopt.geonppang.presentation.type.UserRoleType
import com.sopt.geonppang.util.AmplitudeUtils
import com.sopt.geonppang.util.UiState
import com.sopt.geonppang.util.binding.BindingBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class LoginNeededDialog(
    private val loginNeededType: LoginNeededType
) :
    BindingBottomSheetDialogFragment<DialogBottomLoginNeededBinding>(R.layout.dialog_bottom_login_needed) {

    @Inject
    lateinit var kakaoSignService: KakaoAuthService
    private lateinit var gpDataSource: GPDataSource
    private val authViewModel by viewModels<AuthViewModel>()
    private val filterViewModel by viewModels<FilterSettingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner

        initLayout()
        addListener()
        collectData()
    }

    private fun initLayout() {
        binding.loginNeedType = loginNeededType
    }

    private fun addListener() {
        binding.ivDismiss.setOnClickListener { dismiss() }

        binding.btnStartKakao.setOnClickListener {
            kakaoSignService.startKakaoLogin(authViewModel::signUp)
        }

        binding.tvSignupWithEmail.setOnClickListener {
            moveToSignUp()
        }

        binding.tvLoginWithEmail.setOnClickListener {
            moveToLogIn()
        }
    }

    private fun collectData() {
        gpDataSource = GPDataSource(requireContext())
        // 카카오 회원 가입, 로그인
        authViewModel.authRoleType.flowWithLifecycle(lifecycle).onEach { role ->
            when (role) {
                // 카카오 회원가입인 경우 닉네임 페이지로 이동
                AuthRoleType.ROLE_GUEST -> {
                    moveToNickNameSetting()
                }

                // 카카오 로그인인 경우 홈으로 이동
                AuthRoleType.ROLE_MEMBER -> {
                    AmplitudeUtils.trackEvent(SignActivity.LOGIN_APP)
                    if (filterViewModel.filterStatus.value == true) {
                        gpDataSource.userRoleType = UserRoleType.FILTER_SELECTED_MEMBER.name
                    } else
                        gpDataSource.userRoleType = UserRoleType.FILTER_UNSELECTED_MEMBER.name
                    moveToMain()
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)

        authViewModel.signUpState.flowWithLifecycle(lifecycle).onEach { signUpState ->
            when (signUpState) {
                is UiState.Success -> {
                    authViewModel.setAutoLogin()
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun moveToNickNameSetting() {
        startActivity(Intent(requireContext(), SignUpNicknameActivity::class.java))
    }

    private fun moveToMain() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun moveToSignUp() {
        gpDataSource = GPDataSource(requireContext())
        gpDataSource.platformType = PlatformType.NONE.name
        startActivity(Intent(requireContext(), SignUpActivity::class.java))
    }

    private fun moveToLogIn() {
        startActivity(Intent(requireContext(), LoginActivity::class.java))
    }
}
