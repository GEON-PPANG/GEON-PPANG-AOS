package com.sopt.geonppang.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.sopt.geonppang.R
import com.sopt.geonppang.data.service.KakaoAuthService
import com.sopt.geonppang.databinding.ActivitySignBinding
import com.sopt.geonppang.presentation.login.LoginActivity
import com.sopt.geonppang.presentation.signup.SignUpActivity
import com.sopt.geonppang.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignActivity :
    BindingActivity<ActivitySignBinding>(R.layout.activity_sign) {
    @Inject
    lateinit var kakaoAuthService : KakaoAuthService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KakaoSdk.init(this, this.getString(R.string.kakao_app_key))
        addListeners()
    }

    private fun addListeners() {
        binding.btnStartWithKakao.setOnClickListener {
            kakaoAuthService.loginKakao()
        }
        binding.tvLoginWithEmail.setOnClickListener {
            moveToLogin()
        }
        binding.tvSignupWithEmail.setOnClickListener {
            moveToSignUp()
        }
    }

    private fun moveToSignUp() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    private fun moveToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}
