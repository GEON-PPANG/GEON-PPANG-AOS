package com.sopt.geonppang.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivitySignupBinding
import com.sopt.geonppang.util.binding.BindingActivity
import com.sopt.geonppang.util.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity :
    BindingActivity<ActivitySignupBinding>(R.layout.activity_signup) {
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        addListeners()
        addObserver()
    }

    private fun addListeners() {
        binding.root.setOnClickListener {
            hideKeyboard(it)
        }
        binding.layoutSignupPassword.setOnClickListener {
            hideKeyboard(it)
        }
        binding.layoutSignupContainer.setOnClickListener {
            hideKeyboard(it)
        }
        binding.btnNext.setOnClickListener {
            moveToNickname()
        }
        binding.toolbar.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun addObserver() {
        viewModel.email.observe(this) {
            if (viewModel.isEmailUsable.value != null) {
                viewModel.initEmail()
            }
        }

        viewModel.isEmailUsable.observe(this) {
            val emailValidationFail =
                ContextCompat.getDrawable(this, R.drawable.background_need_change_status)
            val emailValidationTrue = ContextCompat.getDrawable(
                this,
                R.drawable.background_email_double_check_true_status
            )

            if (viewModel.isEmailUsable.value == true) {
                binding.tvEmailText.setTextColor(ContextCompat.getColor(this, R.color.main_3))
                binding.linearEmail.background = emailValidationTrue
            } else {
                binding.tvEmailText.setTextColor(ContextCompat.getColor(this, R.color.error))
                binding.linearEmail.background = emailValidationFail
            }
        }
    }

    private fun setTextColor() {
        Log.e("ddd", "dddddddddddddd")
        if (viewModel.isEmailUsable.value == true) {
            binding.tvEmailText.setTextColor(ContextCompat.getColor(this, R.color.main_3))
        } else {
            binding.tvEmailText.setTextColor(ContextCompat.getColor(this, R.color.error))
        }
    }

    private fun moveToNickname() {
        val intent = Intent(this, SignUpNicknameActivity::class.java)
        intent.putExtra(EMAIL, viewModel.email.value)
        intent.putExtra(PASSWORD, viewModel.password.value)
        startActivity(intent)
    }

    companion object {
        const val EMAIL = "email"
        const val PASSWORD = "password"
    }
}
