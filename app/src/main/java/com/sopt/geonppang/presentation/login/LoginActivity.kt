package com.sopt.geonppang.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityLoginBinding
import com.sopt.geonppang.presentation.signup.SignUpActivity
import com.sopt.geonppang.util.binding.BindingActivity
import com.sopt.geonppang.util.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity :
    BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val viewModel: LogInViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        addListener()
    }

    private fun addListener() {
        binding.root.setOnClickListener {
            hideKeyboard(it)
        }
        binding.tvGotoSignup.setOnClickListener {
            moveToSignup()
        }
    }

    private fun moveToSignup() {
        startActivity(Intent(this, SignUpActivity::class.java))
        finish()
    }
}
