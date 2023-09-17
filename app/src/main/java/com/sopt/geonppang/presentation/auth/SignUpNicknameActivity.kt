package com.sopt.geonppang.presentation.auth

import android.content.Intent
import android.os.Bundle
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

        viewModel.isNicknameDuplicated.observe(this) {
            if (it == true) {
                showNicknameSuccessDialog()
            } else {
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
