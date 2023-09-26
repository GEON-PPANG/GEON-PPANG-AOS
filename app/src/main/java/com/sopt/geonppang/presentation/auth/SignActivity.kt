package com.sopt.geonppang.presentation.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.geonppang.R
import com.sopt.geonppang.data.datasource.local.GPDataSource
import com.sopt.geonppang.data.service.KakaoAuthService
import com.sopt.geonppang.databinding.ActivitySignBinding
import com.sopt.geonppang.presentation.MainActivity
import com.sopt.geonppang.presentation.login.LoginActivity
import com.sopt.geonppang.presentation.type.AuthRoleType
import com.sopt.geonppang.presentation.type.PlatformType
import com.sopt.geonppang.util.AmplitudeUtils
import com.sopt.geonppang.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class SignActivity :
    BindingActivity<ActivitySignBinding>(R.layout.activity_sign) {
    @Inject
    lateinit var kakaoAuthService: KakaoAuthService
    private val authViewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addListeners()
        collectData()
    }

    private fun addListeners() {
        binding.btnStartKakao.setOnClickListener {
            kakaoAuthService.startKakaoLogin(authViewModel::signUp)
            AmplitudeUtils.trackEventWithProperties(START_SIGNUP, SIGNUP_TYPE, KAKAO)
        }
        binding.tvLoginWithEmail.setOnClickListener {
            moveToLogin()
        }
        binding.tvSignupWithEmail.setOnClickListener {
            moveToSignUp()
            AmplitudeUtils.trackEventWithProperties(START_SIGNUP, SIGNUP_TYPE, EMAIL)
        }
    }

    private fun collectData() {
        authViewModel.authRoleType.flowWithLifecycle(lifecycle).onEach { role ->
            when (role) {
                AuthRoleType.GUEST -> {
                    moveToNickNameSetting()
                }

                AuthRoleType.USER -> {
                    authViewModel.setAutoLogin()
                    moveToMain()
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun moveToSignUp() {
        val gpDataSource = GPDataSource(this)
        gpDataSource.platformType = PlatformType.NONE.name
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    private fun moveToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun moveToNickNameSetting() {
        startActivity(Intent(this, SignUpNicknameActivity::class.java))
    }

    private fun moveToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    companion object {
        const val START_SIGNUP = "start_signup"
        const val SIGNUP_TYPE = "signup_type"
        const val EMAIL = "email"
        const val KAKAO = "kakao"
    }
}
