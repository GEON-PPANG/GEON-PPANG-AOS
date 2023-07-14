package com.sopt.geonppang.presentation.signup.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.DialogBottomSignupNicknameBinding
import com.sopt.geonppang.presentation.signup.viewmodel.SignUpViewModel
import com.sopt.geonppang.util.binding.BindingBottomSheetDialogFragment

class SignUpNicknameBottomSheetDialog :
    BindingBottomSheetDialogFragment<DialogBottomSignupNicknameBinding>(
        R.layout.dialog_bottom_signup_nickname
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
