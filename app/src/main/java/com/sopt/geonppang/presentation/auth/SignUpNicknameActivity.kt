package com.sopt.geonppang.presentation.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivitySignupNicknameBinding
import com.sopt.geonppang.presentation.type.PlatformType
import com.sopt.geonppang.util.AmplitudeUtils
import com.sopt.geonppang.util.UiState
import com.sopt.geonppang.util.binding.BindingActivity
import com.sopt.geonppang.util.extension.hideKeyboard
import com.sopt.geonppang.util.extension.setOnSingleClickListener
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
        collectData()
    }

    private fun addListeners() {
        binding.root.setOnClickListener {
            hideKeyboard(it)
        }

        binding.imgBackArrow.setOnClickListener {
            finish()
        }

        binding.btnNext.setOnSingleClickListener {
            completeSignUp()
        }
    }

    private fun collectData() {
        viewModel.signUpState.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    // 회원가입 성공시에만 자동 로그인 설정 (소셜 회원가입도 닉네임까지 설정이 완료된 시점에)
                    viewModel.setAutoLogin()
                    AmplitudeUtils.trackEventWithProperties(
                        COMPLETE_NICKNAME,
                        NICKNAME,
                        viewModel.nickname.value
                    )
                    AmplitudeUtils.trackEvent(COMPLETE_SIGNUP)
                    moveToWelcome()
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)

        viewModel.isNicknameUsable.flowWithLifecycle(lifecycle).onEach { isNicknameUsable ->
            when (isNicknameUsable) {
                is UiState.Success -> {
                    showNicknameSuccessDialog()
                }

                is UiState.Error -> {
                    showNicknameFailDialog()
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)

        viewModel.nickname.flowWithLifecycle(lifecycle).onEach {
            if (viewModel.isNicknameUsable.value != UiState.Loading) {
                viewModel.initNickname()
            }
        }.launchIn(lifecycleScope)

        viewModel.memberId.flowWithLifecycle(lifecycle).onEach { memberId ->
            if (memberId != null)
                AmplitudeUtils.setUserId(GUNBBANG + memberId)
        }.launchIn(lifecycleScope)
    }

    private fun completeSignUp() {
        when (viewModel.platformType) {
            // 카카오 회원가입
            PlatformType.KAKAO.name -> {
                viewModel.settingNickName()
            }

            // 자체 회원가입
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
        const val NICKNAME = "nickname"
        const val EMAIL = "email"
        const val PASSWORD = "password"
        const val COMPLETE_NICKNAME = "complete_nickname"
        const val COMPLETE_SIGNUP = "complete_signup"
        const val GUNBBANG = "gunbbang"
    }
}
