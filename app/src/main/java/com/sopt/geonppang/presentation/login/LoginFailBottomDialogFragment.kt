package com.sopt.geonppang.presentation.login

import android.os.Bundle
import android.view.View
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.DialogBottomLoginFailBinding
import com.sopt.geonppang.util.binding.BindingBottomSheetDialogFragment

class LoginFailBottomDialogFragment :
    BindingBottomSheetDialogFragment<DialogBottomLoginFailBinding>(R.layout.dialog_bottom_login_fail) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner

        addListeners()
    }

    private fun addListeners() {
        binding.btnLoginFailConfirm.setOnClickListener {
            dismiss()
            requireActivity().finish()
        }
    }
}
