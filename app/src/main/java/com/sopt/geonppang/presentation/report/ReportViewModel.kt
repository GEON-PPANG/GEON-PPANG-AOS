package com.sopt.geonppang.presentation.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.data.model.request.RequestReport
import com.sopt.geonppang.domain.repository.ReportRepository
import com.sopt.geonppang.presentation.type.ReportCategoryType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportRepository: ReportRepository
) : ViewModel() {
    private val _reportCategory = MutableStateFlow<ReportCategoryType?>(null)
    val reportCategory get() = _reportCategory.asStateFlow()
    val reportContent = MutableStateFlow("")
    private val _isReportCompleted = MutableStateFlow<Boolean?>(null)
    val isReportCompleted get() = _isReportCompleted.asStateFlow()

    fun setReportCategory(reportCategoryType: ReportCategoryType) {
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
                        _isReportCompleted.value = true
                    }.onFailure { throwable ->
                        Timber.e(throwable.message)
                    }
                }
            }
        }
    }
}
