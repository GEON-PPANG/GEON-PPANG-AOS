package com.sopt.geonppang.presentation.mypage

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.DialogLogoutBinding
import com.sopt.geonppang.presentation.SignActivity

class LogoutDialog(context: Context) : Dialog(context) {
    private lateinit var binding: DialogLogoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogLogoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
        )
        window?.setBackgroundDrawableResource(R.color.dialog_background)
        setCancelable(false)
        setCanceledOnTouchOutside(true)
    }

    private fun addListeners() {
        binding.includeDialogLogout.btnDialogLeft.setOnClickListener {
            dismiss()
        }

        binding.includeDialogLogout.btnDialogRight.setOnClickListener {
            dismiss()
            moveToSign()
        }
    }

    private fun moveToSign() {
        context.startActivity(Intent(context, SignActivity::class.java))
    }
}
