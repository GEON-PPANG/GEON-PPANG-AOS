package com.sopt.geonppang.presentation.report

import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityReportBinding
import com.sopt.geonppang.util.binding.BindingActivity
import com.sopt.geonppang.util.extension.hideKeyboard

class ReportActivity : BindingActivity<ActivityReportBinding>(R.layout.activity_report) {
    private val viewModel: ReportViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        addListeners()
    }

    private fun addListeners() {
        binding.etReportContent.setOnClickListener {
            binding.scrollViewReport.smoothScrollTo(0, binding.etReportContent.top)
        }

        binding.etReportContent.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.scrollViewReport.smoothScrollTo(0, binding.etReportContent.top)
            }
        }

        binding.btnReport.setOnClickListener {
            showReportSucessBottomDialog()
        }

        binding.includeReportToolbar.ivBack.setOnClickListener {
            finish()
        }

        binding.layoutReport.setOnClickListener {
            hideKeyboard(it)
        }
    }

    private fun showReportSucessBottomDialog() {
        ReportSuccessBottomDialogFragment().show(supportFragmentManager, REPORT_SUCCESS)
    }

    companion object {
        const val REPORT_SUCCESS = "reportSuccessDialog"
    }
}
