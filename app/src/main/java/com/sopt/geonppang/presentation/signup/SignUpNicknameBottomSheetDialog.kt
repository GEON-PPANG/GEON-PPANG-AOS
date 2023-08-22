package com.sopt.geonppang.presentation.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.DialogBottomSignupNicknameSuccessBinding
import com.sopt.geonppang.util.binding.BindingBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpNicknameBottomSheetDialog :
    BindingBottomSheetDialogFragment<DialogBottomSignupNicknameSuccessBinding>(
        R.layout.dialog_bottom_signup_nickname_success
    ) {
    private val viewModel: SignUpViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        addListener()
    }

    private fun addListener() {
        binding.btnCheck.setOnClickListener { dismiss() }
    }
}
