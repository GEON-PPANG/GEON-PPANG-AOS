package com.sopt.geonppang.presentation.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.DialogBottomSignupNicknameBinding
import com.sopt.geonppang.util.binding.BindingBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpNicknameBottomSheetDialog :
    BindingBottomSheetDialogFragment<DialogBottomSignupNicknameBinding>(
        R.layout.dialog_bottom_signup_nickname
    ) {
    private val viewModel: AuthViewModel by viewModels()

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
