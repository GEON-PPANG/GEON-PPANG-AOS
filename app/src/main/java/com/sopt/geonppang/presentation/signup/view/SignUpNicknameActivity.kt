package com.sopt.geonppang.presentation.signup.view

import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivitySignupNicknameBinding
import com.sopt.geonppang.presentation.signup.viewmodel.SignUpViewModel
import com.sopt.geonppang.util.binding.BindingActivity
import com.sopt.geonppang.util.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpNicknameActivity :
    BindingActivity<ActivitySignupNicknameBinding>(R.layout.activity_signup_nickname) {
    private val viewModel: SignUpViewModel by viewModels()

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

        binding.btnDoubleCheck.setOnClickListener {
            val bottomSheetDialog = SignUpNicknameBottomSheetDialog()
            bottomSheetDialog.show(supportFragmentManager, bottomSheetDialog.tag)
        }
    }
}
