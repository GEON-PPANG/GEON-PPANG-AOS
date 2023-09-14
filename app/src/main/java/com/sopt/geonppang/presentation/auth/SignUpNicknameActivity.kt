package com.sopt.geonppang.presentation.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivitySignupNicknameBinding
import com.sopt.geonppang.util.binding.BindingActivity
import com.sopt.geonppang.util.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpNicknameActivity :
    BindingActivity<ActivitySignupNicknameBinding>(R.layout.activity_signup_nickname) {
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

        binding.btnDoubleCheck.setOnClickListener {
            showNicknameSuccessDialog()
        }

        binding.imgBackArrow.setOnClickListener {
            finish()
        }

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            intent.putExtra(NICKNAME, viewModel.inputNickname.value)
            startActivity(intent)
        }
    }

    private fun showNicknameSuccessDialog() {
        SignUpNicknameBottomSheetDialog().show(supportFragmentManager, NICKNAME_AVAILIABLE)
    }

    companion object {
        const val NICKNAME_AVAILIABLE = "nicknameAvailiable"
        const val NICKNAME = "nickName"
    }
}
