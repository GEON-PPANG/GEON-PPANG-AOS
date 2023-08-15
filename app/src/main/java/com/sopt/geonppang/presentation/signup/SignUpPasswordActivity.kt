package com.sopt.geonppang.presentation.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivitySignupBinding
import com.sopt.geonppang.util.KeyboardVIsibilityUtils
import com.sopt.geonppang.util.binding.BindingActivity
import com.sopt.geonppang.util.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpPasswordActivity :
    BindingActivity<ActivitySignupBinding>(R.layout.activity_signup) {
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
        binding.svSignupContainer.setOnClickListener {
            hideKeyboard(it)
        }
        binding.btnNext.setOnClickListener {
            moveToNickname()
        }
        binding.toolbar.ivBack.setOnClickListener {
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
                binding.svSignup.run {
                    smoothScrollTo(scrollX, scrollY + keyboardHeight)
                }
            })
    }

    override fun onDestroy() {
        keyboardVIsibilityUtils.detachKeyboardListeners()
        super.onDestroy()
    }
}
