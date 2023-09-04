package com.sopt.geonppang.presentation.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.DialogMiddleBinding
import com.sopt.geonppang.presentation.SignActivity
import com.sopt.geonppang.presentation.type.DialogType
import com.sopt.geonppang.util.binding.BindingDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogoutDialog() : BindingDialogFragment<DialogMiddleBinding>(R.layout.dialog_middle) {
    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        addListeners()
        addObservers()
    }

    private fun initLayout() {
        binding.dialogType = DialogType.LOGOUT
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
        )
        dialog?.window?.setBackgroundDrawableResource(R.color.dialog_background)
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(true)
    }

    private fun addListeners() {
        binding.btnDialogLeft.setOnClickListener {
            dismiss()
        }

        binding.btnDialogRight.setOnClickListener {
            viewModel.logout()
        }
    }

    private fun addObservers() {
        viewModel.isLogoutCompleted.observe(this) { isLogoutCompleted ->
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
