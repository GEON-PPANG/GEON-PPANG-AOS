package com.sopt.geonppang.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.geonppang.R
import com.sopt.geonppang.data.datasource.local.GPDataSource
import com.sopt.geonppang.databinding.ActivityLoginBinding
import com.sopt.geonppang.presentation.MainActivity
import com.sopt.geonppang.presentation.auth.SignUpActivity
import com.sopt.geonppang.presentation.filterSetting.FilterSettingViewModel
import com.sopt.geonppang.presentation.type.UserRoleType
import com.sopt.geonppang.util.AmplitudeUtils
import com.sopt.geonppang.util.UiState
import com.sopt.geonppang.util.binding.BindingActivity
import com.sopt.geonppang.util.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LoginActivity :
    BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val viewModel: LogInViewModel by viewModels()
    private val filterViewModel: FilterSettingViewModel by viewModels()
    private lateinit var gpDataSource: GPDataSource
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
        gpDataSource = GPDataSource(this)
        viewModel.loginState.flowWithLifecycle(lifecycle).onEach { loginState ->
            when (loginState) {
                is UiState.Success -> {
                    AmplitudeUtils.trackEvent(LOGIN_APP)
                    if (filterViewModel.filterStatus.value == true) {
                        gpDataSource.userRoleType = UserRoleType.FILTER_SELECTED_MEMBER.name
                    } else
                        gpDataSource.userRoleType = UserRoleType.FILTER_UNSELECTED_MEMBER.name
                    moveToHome()
                    viewModel.setAutoLogin()
                }

                is UiState.Error -> {
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
        val intent = Intent(this, MainActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun moveToSignup() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    companion object {
        const val LOGIN_FAIL = "loginFail"
        const val LOGIN_APP = "login_app"
    }
}
