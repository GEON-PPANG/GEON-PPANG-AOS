package com.sopt.geonppang.presentation.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivitySignupPasswordBinding
import com.sopt.geonppang.util.KeyboardVIsibilityUtils
import com.sopt.geonppang.util.binding.BindingActivity
import com.sopt.geonppang.util.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpPasswordActivity :
    BindingActivity<ActivitySignupPasswordBinding>(R.layout.activity_signup_password) {
    private val viewModel: SignUpViewModel by viewModels()
    private lateinit var keyboardVIsibilityUtils: KeyboardVIsibilityUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        addListeners()
        requestFocus()
    }

    private fun addListeners() {
        binding.root.setOnClickListener {
            hideKeyboard(it)
        }
        binding.layoutSignupPassword.setOnClickListener {
            hideKeyboard(it)
        }
        binding.svSignupPasswordContainer.setOnClickListener {
            hideKeyboard(it)
        }
        binding.btnNext.setOnClickListener {
            moveToNickname()
        }
        binding.ivSignupPasswordArrowLeft.setOnClickListener {
            moveToEmail()
        }
    }

    private fun moveToNickname() {
        startActivity(Intent(this, SignUpNicknameActivity::class.java))
        finish()
    }

    private fun moveToEmail() {
        startActivity(Intent(this, SignUpEmailActivity::class.java))
        finish()
    }

    private fun requestFocus() {
        keyboardVIsibilityUtils =
            KeyboardVIsibilityUtils(window, onShowKeyboard = { keyboardHeight ->
                binding.svSignupPassword.run {
                    smoothScrollTo(scrollX, scrollY + keyboardHeight)
                }
            })
    }

    override fun onDestroy() {
        keyboardVIsibilityUtils.detachKeyboardListeners()
        super.onDestroy()
    }
}
