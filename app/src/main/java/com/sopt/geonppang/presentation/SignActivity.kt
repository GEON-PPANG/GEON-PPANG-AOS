package com.sopt.geonppang.presentation

import android.content.Intent
import android.os.Bundle
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivitySignBinding
import com.sopt.geonppang.util.binding.BindingActivity

class SignActivity :
    BindingActivity<ActivitySignBinding>(R.layout.activity_sign) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // addListeners()
    }

    // TODO 회원가입으로 넘어가도록 구현 예정
    private fun addListeners() {
        binding.btnSignup.setOnClickListener { }
    }

    private fun moveToSignUp() {
        startActivity(Intent())
    }
}
