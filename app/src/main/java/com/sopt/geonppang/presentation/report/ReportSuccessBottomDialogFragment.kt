package com.sopt.geonppang.presentation.report

import android.os.Bundle
import android.view.View
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.DialogBottomReportSuccessBinding
import com.sopt.geonppang.util.binding.BindingBottomSheetDialogFragment

class ReportSuccessBottomDialogFragment :
    BindingBottomSheetDialogFragment<DialogBottomReportSuccessBinding>(R.layout.dialog_bottom_report_success) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner

        addListeners()
    }

    private fun addListeners() {
        binding.btnReportSuccess.setOnClickListener {
            dismiss()
            requireActivity().finish()
        }
    }
}
