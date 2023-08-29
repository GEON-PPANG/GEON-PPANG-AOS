package com.sopt.geonppang.presentation.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivitySignupNicknameBinding
import com.sopt.geonppang.presentation.filterSetting.FilterSettingActivity
import com.sopt.geonppang.presentation.type.FilterInfoType
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
            moveToPassword()
        }

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, FilterSettingActivity::class.java)
            intent.putExtra(FILTER_INFO, FilterInfoType.ONBOARDING.activityName)
            intent.putExtra(MAX_PAGE, FilterInfoType.ONBOARDING.maxPage)
            startActivity(intent)
            viewModel.saveUserNickname()
        }
    }

    private fun moveToPassword() {
        startActivity(Intent(this, SignUpActivity::class.java))
        finish()
    }

    companion object {
        const val FILTER_INFO = "filterInfo"
        const val MAX_PAGE = "maxPage"
    }
}
