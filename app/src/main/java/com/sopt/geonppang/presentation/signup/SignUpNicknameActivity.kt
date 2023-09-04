package com.sopt.geonppang.presentation.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivitySignupNicknameBinding
import com.sopt.geonppang.presentation.filter.WelcomeActivity
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

        binding.imgBackArrow.setOnClickListener {
            finish()
        }

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            intent.putExtra(NICKNAME, viewModel.nickname.value)
            startActivity(intent)
        }
    }

    private fun moveToPassword() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    companion object {
        const val NICKNAME = "nickName"
    }
}
