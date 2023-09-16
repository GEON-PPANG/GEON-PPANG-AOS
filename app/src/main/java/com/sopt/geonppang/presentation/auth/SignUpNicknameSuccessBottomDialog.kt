package com.sopt.geonppang.presentation.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.DialogBottomSignupNicknameSuccessBinding
import com.sopt.geonppang.util.binding.BindingBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpNicknameSuccessBottomDialog :
    BindingBottomSheetDialogFragment<DialogBottomSignupNicknameSuccessBinding>(
        R.layout.dialog_bottom_signup_nickname_success
    ) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner

        addListener()
    }

    private fun addListener() {
        binding.btnCheck.setOnClickListener { dismiss() }
    }
}
