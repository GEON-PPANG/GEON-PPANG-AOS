package com.sopt.geonppang.presentation.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.data.model.request.RequestReport
import com.sopt.geonppang.domain.repository.ReportRepository
import com.sopt.geonppang.presentation.type.ReportCategoryType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportRepository: ReportRepository
) : ViewModel() {
    private val _reportCategory: MutableLiveData<ReportCategoryType?> = MutableLiveData()
    val reportCategory: LiveData<ReportCategoryType?> = _reportCategory
    val reportContent = MutableLiveData("")
    private val _isReportCompleted = MutableLiveData<Boolean>()
    val isReportCompleted: LiveData<Boolean> = _isReportCompleted

    private fun setIsReportCompleted(value: Boolean) {
        _isReportCompleted.value = value
    }

    fun setReportCategory(reportCategoryType: ReportCategoryType) {
        _reportCategory.value = reportCategoryType
    }

    fun reportReview(reviewId: Int) {
        viewModelScope.launch {
            reportContent.value?.let { reportContent ->
                reportCategory.value?.let { reportCategory ->
                    reportRepository.reportReview(
                        reviewId,
                        RequestReport(content = reportContent, reportCategory = reportCategory.name)
                    ).onSuccess { responseReport ->
                        setIsReportCompleted(true)
                    }.onFailure { throwable ->
                        Timber.e(throwable.message)
                    }
                }
            }
        }
    }
}
