package com.sopt.geonppang.presentation.auth

import android.content.Intent
import android.os.Bundle
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
        binding.tvEmailErrorMsg.setOnClickListener{
            viewModel.isNicknameDuplicated.observe(this){
                if(it == true){
                    binding.tvEmailErrorMsg.setTextColor(ContextCompat.getColor(this, R.color.main_3))
                }
            }
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
