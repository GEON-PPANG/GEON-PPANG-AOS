package com.sopt.geonppang.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivitySignupBinding
import com.sopt.geonppang.util.UiState
import com.sopt.geonppang.util.binding.BindingActivity
import com.sopt.geonppang.util.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SignUpActivity :
    BindingActivity<ActivitySignupBinding>(R.layout.activity_signup) {
    private val viewModel: AuthViewModel by viewModels()
    private var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        addListeners()
        collectData()
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

    private fun collectData() {
        viewModel.isEmailUsable.flowWithLifecycle(lifecycle).onEach { isEmailUsable ->
            val emailValidationFail =
                ContextCompat.getDrawable(this, R.drawable.background_need_change_status)
            val emailValidationTrue = ContextCompat.getDrawable(
                this,
                R.drawable.background_email_double_check_true_status
            )
            val nextButtonTrue = ContextCompat.getColorStateList(this, R.color.main_2)
            val nextButtonFalse = ContextCompat.getColorStateList(this, R.color.gray_200)

            when (isEmailUsable) {
                is UiState.Success -> {
                    with(binding) {
                        tvEmailText.setTextColor(
                            ContextCompat.getColor(
                                root.context,
                                R.color.main_3
                            )
                        )
                        linearEmail.background = emailValidationTrue
                        tvEmailErrorMsg.setTextColor(
                            ContextCompat.getColor(
                                root.context,
                                R.color.main_3
                            )
                        )
                        tvEmailErrorMsg.text = getString(R.string.email_validate_to_use)
                        tvEmailErrorMsg.visibility = View.VISIBLE
                        flag = true
                    }
                }

                is UiState.Error -> {
                    with(binding) {
                        tvEmailText.setTextColor(
                            ContextCompat.getColor(
                                root.context,
                                R.color.error
                            )
                        )
                        linearEmail.background = emailValidationFail
                        tvEmailErrorMsg.setTextColor(
                            ContextCompat.getColor(
                                root.context,
                                R.color.error
                            )
                        )
                        tvEmailErrorMsg.text = getString(R.string.email_already_exist)
                        tvEmailErrorMsg.visibility = View.VISIBLE
                        /*btnNext.isEnabled = false
                        btnNext.backgroundTintList = nextButtonFalse
                        btnNext.setTextColor(ContextCompat.getColor(root.context, R.color.gray_400))*/
                    }
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)

        viewModel.email.flowWithLifecycle(lifecycle).onEach {
            val nextButtonFalse = ContextCompat.getColorStateList(this, R.color.gray_200)
            if (viewModel.isEmailUsable.value != UiState.Loading)
                viewModel.initEmail()

            with(binding) {
                tvEmailErrorMsg.visibility = View.VISIBLE
                tvEmailErrorMsg.text = getString(R.string.email_error_msg)
                btnNext.isEnabled = false
                btnNext.backgroundTintList = nextButtonFalse
                btnNext.setTextColor(ContextCompat.getColor(root.context, R.color.gray_400))
            }

        }.launchIn(lifecycleScope)

        viewModel.passwordCheck.flowWithLifecycle(lifecycle).onEach {
            val nextButtonTrue = ContextCompat.getColorStateList(this, R.color.main_2)
            if(flag && viewModel.isPasswordDoubleCheck()){
                binding.btnNext.isEnabled = true
                binding.btnNext.backgroundTintList = nextButtonTrue
                binding.btnNext.setTextColor(ContextCompat.getColor(this, R.color.white))
            }
        }.launchIn(lifecycleScope)
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
