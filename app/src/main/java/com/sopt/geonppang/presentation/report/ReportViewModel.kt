package com.sopt.geonppang.presentation.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.geonppang.presentation.type.ReportCategoryType

class ReportViewModel : ViewModel() {

    private val _reportCategory: MutableLiveData<ReportCategoryType?> = MutableLiveData()
    val reportCategory: LiveData<ReportCategoryType?> = _reportCategory

    fun setReportCategory(reportCategoryType: ReportCategoryType) {
        _reportCategory.value = reportCategoryType
    }

    val reportContent = MutableLiveData("")
}