package com.sopt.geonppang.presentation

import android.content.Intent
import android.os.Bundle
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivitySignBinding
import com.sopt.geonppang.presentation.login.LoginActivity
import com.sopt.geonppang.presentation.signup.SignUpActivity
import com.sopt.geonppang.util.binding.BindingActivity

class SignActivity :
    BindingActivity<ActivitySignBinding>(R.layout.activity_sign) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addListeners()
    }

    private fun addListeners() {
        binding.btnStartWithKakao.setOnClickListener {
            moveToSignUp()
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
