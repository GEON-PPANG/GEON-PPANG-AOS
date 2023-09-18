package com.sopt.geonppang.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.geonppang.R
import com.sopt.geonppang.data.datasource.local.GPDataSource
import com.sopt.geonppang.databinding.ActivitySignupNicknameBinding
import com.sopt.geonppang.presentation.type.PlatformType
import com.sopt.geonppang.util.UiState
import com.sopt.geonppang.util.binding.BindingActivity
import com.sopt.geonppang.util.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SignUpNicknameActivity :
    BindingActivity<ActivitySignupNicknameBinding>(R.layout.activity_signup_nickname) {
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        addListeners()
        addObserver()
        collectData()
    }

    private fun addListeners() {
        binding.root.setOnClickListener {
            hideKeyboard(it)
        }

        binding.imgBackArrow.setOnClickListener {
            finish()
        }

        binding.btnNext.setOnClickListener {
            completeSignUp()
        }
    }

    private fun collectData() {
        viewModel.signUpState.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    moveToWelcome()
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun addObserver() {
        /*중복확인 값이 저장되는 현상..
        * 1. 귤 입력
        * 2. 성공 바텀 시트
        * 2. 닉네임 하나라도 지우면 시작하기 버튼은 다시 다음버튼으로 비활성화*/
        viewModel.isNicknameUsable.observe(this) {
            if (it == true) {
                Log.e("nicknamebottom", "success")
                showNicknameSuccessDialog()
            }
            if (it == false) {
                Log.e("nicknamebottom", "false")
                showNicknameFailDialog()
            }
        }
    }

    private fun completeSignUp() {
        val gpDataSource = GPDataSource(this)
        when (gpDataSource.platformType) {
            PlatformType.KAKAO.name -> {
                viewModel.settingNickName()
            }

            PlatformType.NONE.name -> {
                val email = intent.getStringExtra(EMAIL)
                val password = intent.getStringExtra(PASSWORD)
                viewModel.signUp(
                    PlatformType.NONE,
                    "",
                    email ?: "",
                    password ?: "",
                    viewModel.nickname.value ?: ""
                )
            }
        }
    }

    private fun moveToWelcome() {
        val intent = Intent(this, WelcomeActivity::class.java)
        intent.putExtra(NICKNAME, viewModel.nickname.value)
        startActivity(intent)
    }

    private fun showNicknameSuccessDialog() {
        SignUpNicknameSuccessBottomDialog().show(supportFragmentManager, "nicknameSuccessDialog")
    }

    private fun showNicknameFailDialog() {
        SignUpNicknameFailBottomDialog().show(supportFragmentManager, "nicknameFailDialog")
    }

    companion object {
        const val NICKNAME = "nickName"
        const val EMAIL = "email"
        const val PASSWORD = "password"
    }
}
