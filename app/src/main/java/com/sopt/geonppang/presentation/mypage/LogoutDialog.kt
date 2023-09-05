package com.sopt.geonppang.presentation.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.DialogMiddleBinding
import com.sopt.geonppang.presentation.auth.SignActivity
import com.sopt.geonppang.presentation.type.DialogType
import com.sopt.geonppang.util.binding.BindingDialogFragment

class LogoutDialog : BindingDialogFragment<DialogMiddleBinding>(R.layout.dialog_middle) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        binding.dialogType = DialogType.LOGOUT
    }

    private fun addListeners() {
        binding.btnDialogNo.setOnClickListener {
            dismiss()
        }

        binding.btnDialogYes.setOnClickListener {
            dismiss()
            moveToSign()
        }
    }

    private fun moveToSign() {
        context?.startActivity(Intent(context, SignActivity::class.java))
    }
}
