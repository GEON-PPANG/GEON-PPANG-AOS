package com.sopt.geonppang.presentation.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.geonppang.R
import com.sopt.geonppang.data.service.KakaoAuthService
import com.sopt.geonppang.databinding.ActivitySignBinding
import com.sopt.geonppang.presentation.MainActivity
import com.sopt.geonppang.presentation.login.LoginActivity
import com.sopt.geonppang.presentation.type.AuthRoleType
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
        binding.btnStartWithKakao.setOnClickListener {
            kakaoAuthService.startKakaoLogin(authViewModel::singUp)
        }
        binding.tvLoginWithEmail.setOnClickListener {
            moveToLogin()
        }
        binding.tvSignupWithEmail.setOnClickListener {
            moveToSignUp()
        }
    }

    private fun collectData() {
        authViewModel.authRoleType.flowWithLifecycle(lifecycle).onEach { role ->
            when (role) {
                AuthRoleType.GUEST -> {
                    moveToNickNameSetting()
                }

                AuthRoleType.USER -> {
                    moveToMain()
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun moveToSignUp() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    private fun moveToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun moveToNickNameSetting() {
        startActivity(Intent(this, SignUpNicknameActivity::class.java))
    }

    private fun moveToMain() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}
