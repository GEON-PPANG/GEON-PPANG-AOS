package com.sopt.geonppang.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityLoginBinding
import com.sopt.geonppang.presentation.MainActivity
import com.sopt.geonppang.presentation.auth.SignUpActivity
import com.sopt.geonppang.util.binding.BindingActivity
import com.sopt.geonppang.util.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LoginActivity :
    BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val viewModel: LogInViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        addListener()
        collectData()
    }

    private fun addListener() {
        binding.root.setOnClickListener {
            hideKeyboard(it)
        }
        binding.tvGotoSignup.setOnClickListener {
            moveToSignup()
        }
        binding.toolbar.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun collectData() {
        viewModel.loginState.flowWithLifecycle(lifecycle).onEach { loginState ->
            when (loginState) {
                true -> {
                    moveToHome()
                }

                false -> {
                    showLoginFailDialog()
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun showLoginFailDialog() {
        LoginFailBottomDialogFragment().show(supportFragmentManager, LOGIN_FAIL)
    }


    private fun moveToHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun moveToSignup() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    companion object {
        const val LOGIN_FAIL = "loginFail"
    }
}
