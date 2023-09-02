package com.sopt.geonppang.presentation.report

import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityReportBinding
import com.sopt.geonppang.util.binding.BindingActivity

class ReportActivity : BindingActivity<ActivityReportBinding>(R.layout.activity_report) {
    private val viewModel: ReportViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        addListeners()
    }

    private fun addListeners() {
        binding.btnReport.setOnClickListener {
            showReportSucessBottomDialog()
        }

        binding.includeReportToolbar.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun showReportSucessBottomDialog() {
        ReportSuccessBottomDialogFragment().show(supportFragmentManager, REPORT_SUCCESS)
    }

    companion object {
        const val REPORT_SUCCESS = "reportSuccessDialog"
    }
}
