package com.sopt.geonppang.presentation.report

import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityReportBinding
import com.sopt.geonppang.presentation.detail.DetailActivity
import com.sopt.geonppang.util.binding.BindingActivity
import com.sopt.geonppang.util.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportActivity : BindingActivity<ActivityReportBinding>(R.layout.activity_report) {
    private val viewModel: ReportViewModel by viewModels()
    private var reviewId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        reviewId = intent.getIntExtra(DetailActivity.REVIEW_ID, -1)

        addListeners()
        addObservers()
    }

    private fun addListeners() {
        binding.etReportContent.setOnClickListener {
            binding.svReport.smoothScrollTo(0, binding.etReportContent.top)
        }

        binding.etReportContent.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.svReport.smoothScrollTo(0, binding.etReportContent.top)
            }
        }

        binding.btnReport.setOnClickListener {
            viewModel.reportReview(reviewId)
        }

        binding.includeReportToolbar.ivBack.setOnClickListener {
            finish()
        }

        binding.layoutReport.setOnClickListener {
            hideKeyboard(it)
        }
    }

    private fun addObservers() {
        viewModel.isReportCompleted.observe(this) { isReportCompleted ->
            if (isReportCompleted) {
                showReportSuccessBottomDialog()
            }
        }
    }

    private fun showReportSuccessBottomDialog() {
        ReportSuccessBottomDialogFragment().show(supportFragmentManager, REPORT_SUCCESS)
    }

    companion object {
        const val REPORT_SUCCESS = "reportSuccessDialog"
    }
}
