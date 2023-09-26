package com.sopt.geonppang.presentation.report

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityReportBinding
import com.sopt.geonppang.presentation.detail.DetailActivity
import com.sopt.geonppang.util.AmplitudeUtils
import com.sopt.geonppang.util.UiState
import com.sopt.geonppang.util.binding.BindingActivity
import com.sopt.geonppang.util.extension.hideKeyboard
import com.sopt.geonppang.util.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

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
        collectData()
    }

    private fun addListeners() {
        binding.etReportContent.setOnClickListener {
            binding.svReport.smoothScrollTo(0, binding.etReportContent.top)
        }

        binding.etReportContent.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                AmplitudeUtils.trackEvent(CLICK_REVIEW_REPORT_TEXT)
                binding.svReport.smoothScrollTo(0, binding.etReportContent.top)
            }
        }

        binding.btnReport.setOnSingleClickListener {
            viewModel.reportReview(reviewId)
        }

        binding.includeReportToolbar.ivBack.setOnClickListener {
            AmplitudeUtils.trackEvent(CLICK_REVIEW_REPORT_BACK)
            finish()
        }

        binding.layoutReport.setOnClickListener {
            binding.etReportContent.clearFocus()
            hideKeyboard(it)
        }
    }

    private fun collectData() {
        viewModel.reportState.flowWithLifecycle(lifecycle).onEach { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    AmplitudeUtils.trackEvent(CLICK_REVIEW_REPORT_COMPLETE)
                    AmplitudeUtils.trackEventWithMapProperties(
                        COMPLETE_REVIEW_REPORT,
                        mapOf(OPTION to uiState.data.reportCategory, TEXT to uiState.data.content)
                    )
                    showReportSuccessBottomDialog()
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)

        viewModel.reportCategory.flowWithLifecycle(lifecycle).onEach {
            it?.let { reportCategoryType ->
                AmplitudeUtils.trackEventWithProperties(
                    CLICK_REVIEW_REPORT_OPTION,
                    OPTION,
                    reportCategoryType.name
                )
            }
        }.launchIn(lifecycleScope)
    }

    private fun showReportSuccessBottomDialog() {
        ReportSuccessBottomDialogFragment().show(supportFragmentManager, REPORT_SUCCESS)
    }

    companion object {
        const val REPORT_SUCCESS = "reportSuccessDialog"
        const val CLICK_REVIEW_REPORT_OPTION = "click_reviewreport_option"
        const val CLICK_REVIEW_REPORT_TEXT = "click_reviewreport_text"
        const val CLICK_REVIEW_REPORT_BACK = "click_reviewreport_back"
        const val CLICK_REVIEW_REPORT_COMPLETE = "click_reviewreport_complete"
        const val COMPLETE_REVIEW_REPORT = "complete_reviewreport"
        const val OPTION = "option"
        const val TEXT = "text"
    }
}
