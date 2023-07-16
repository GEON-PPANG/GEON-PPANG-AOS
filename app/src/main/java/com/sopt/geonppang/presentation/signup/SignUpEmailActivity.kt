package com.sopt.geonppang.presentation.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivitySignupEmailBinding
import com.sopt.geonppang.util.binding.BindingActivity
import com.sopt.geonppang.util.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpEmailActivity :
    BindingActivity<ActivitySignupEmailBinding>(R.layout.activity_signup_email) {
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
        binding.btnNext.setOnClickListener {
            moveToPassword()
        }
        binding.btnDoubleCheck.setOnClickListener {
            val bottomSheetDialog = SignUpEmailBottomSheetDialog()
            bottomSheetDialog.show(supportFragmentManager, bottomSheetDialog.tag)
        }
    }

    private fun moveToPassword() {
        startActivity(Intent(this, SignUpPasswordActivity::class.java))
        finish()
    }
}
