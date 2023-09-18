package com.sopt.geonppang.presentation.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.data.model.request.RequestReport
import com.sopt.geonppang.domain.repository.ReportRepository
import com.sopt.geonppang.presentation.type.ReportCategoryType
import com.sopt.geonppang.util.AmplitudeUtils
import com.sopt.geonppang.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportRepository: ReportRepository
) : ViewModel() {
    private val _reportCategory = MutableStateFlow<ReportCategoryType?>(null)
    val reportCategory get() = _reportCategory.asStateFlow()
    val reportContent = MutableStateFlow("")
    private val _reportState = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val reportState get() = _reportState.asStateFlow()

    fun setReportCategory(reportCategoryType: ReportCategoryType) {
        AmplitudeUtils.trackEventWithProperties(
            CLICK_REVIEW_REPORT_OPTION,
            OPTION,
            reportCategoryType.name
        )
        _reportCategory.value = reportCategoryType
    }

    fun reportReview(reviewId: Int) {
        viewModelScope.launch {
            reportContent.value.let { reportContent ->
                reportCategory.value?.let { reportCategory ->
                    reportRepository.reportReview(
                        reviewId,
                        RequestReport(content = reportContent, reportCategory = reportCategory.name)
                    ).onSuccess {
                        _reportState.value = UiState.Success(true)
                    }.onFailure { throwable ->
                        _reportState.value = UiState.Error(throwable.message)
                    }
                }
            }
        }
    }

    companion object {
        const val CLICK_REVIEW_REPORT_OPTION = "click_reviewreport_option"
        const val OPTION = "option"
    }
}
