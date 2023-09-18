package com.sopt.geonppang.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.geonppang.R
import com.sopt.geonppang.data.datasource.local.GPDataSource
import com.sopt.geonppang.databinding.ActivityLoginBinding
import com.sopt.geonppang.presentation.auth.SignUpActivity
import com.sopt.geonppang.presentation.home.HomeFragment
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

        // autoLogin()
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
                    // Log.e("로그인 성공시", "{${loginState}}")
                }

                false -> {
                    // Log.e("로그인 실패시", "{${loginState}}")
                    showLoginFailDialog()
                    viewModel.initLogin()
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun showLoginFailDialog() {
        LoginFailBottomDialogFragment().show(supportFragmentManager, LOGIN_FAIL)
    }

    private fun moveToHome() {
        startActivity(Intent(this, HomeFragment::class.java))
        finish()
    }

    private fun moveToSignup() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    private fun autoLogin() {
        val gpDataSource = GPDataSource(this)
        if (gpDataSource.isLogin)
            moveToHome()
    }

    companion object {
        const val LOGIN_FAIL = "loginFail"
    }
}
