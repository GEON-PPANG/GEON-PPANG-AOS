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
class WithdrawDialog : BindingDialogFragment<DialogMiddleBinding>(R.layout.dialog_middle) {
    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        addListeners()
        addObservers()
    }

    private fun initLayout() {
        binding.dialogType = DialogType.WITHDRAW
    }

    private fun addListeners() {
        binding.btnDialogNo.setOnClickListener {
            dismiss()
        }

        binding.btnDialogYes.setOnClickListener {
            viewModel.withdraw()
        }
    }

    private fun addObservers() {
        viewModel.isWithdrawCompleted.observe(this) { isWithdrawCompleted ->
            if (isWithdrawCompleted) {
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
