package com.sopt.geonppang.presentation.signup.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivitySignupPasswordBinding
import com.sopt.geonppang.presentation.signup.viewmodel.SignUpViewModel
import com.sopt.geonppang.util.binding.BindingActivity
import com.sopt.geonppang.util.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpPasswordActivity :
    BindingActivity<ActivitySignupPasswordBinding>(R.layout.activity_signup_password) {
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        // binding에서 LiveData를 사용할 경우 해당 코드 필요

        addListeners()
    }

    private fun initLayout() {
        TODO("Not yet implemented")
    }

    private fun addListeners() {
        binding.root.setOnClickListener {
            hideKeyboard(it)
        }
        binding.btnNext.setOnClickListener {
            moveToNickname()
        }
    }

    private fun addObservers() {
        TODO("Not yet implemented")
    }

    private fun moveToNickname() {
        startActivity(Intent(this, SignUpNicknameActivity::class.java))
        finish()
    }
}
