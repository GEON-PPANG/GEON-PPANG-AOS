package com.sopt.geonppang.presentation.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.DialogMiddleBinding
import com.sopt.geonppang.presentation.auth.SignActivity
import com.sopt.geonppang.presentation.type.DialogType
import com.sopt.geonppang.util.binding.BindingDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogoutDialog : BindingDialogFragment<DialogMiddleBinding>(R.layout.dialog_middle) {
    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this.viewLifecycleOwner

        initLayout()
        addListeners()
        addObservers()
    }

    private fun initLayout() {
        binding.dialogType = DialogType.LOGOUT
    }

    private fun addListeners() {
        binding.btnDialogNo.setOnClickListener {
            dismiss()
        }

        binding.btnDialogYes.setOnClickListener {
            viewModel.logout()
        }
    }

    private fun addObservers() {
        viewModel.isLogoutCompleted.observe(viewLifecycleOwner) { isLogoutCompleted ->
            if (isLogoutCompleted) {
                moveToSign()
                dismiss()
            }
        }
    }

    private fun moveToSign() {
        val intent = Intent(requireContext(), SignActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
