package com.sopt.geonppang.data.datasource.remote

import com.sopt.geonppang.data.model.request.RequestReport
import com.sopt.geonppang.data.model.response.ResponseReport
import com.sopt.geonppang.data.service.ReportService
import javax.inject.Inject

class ReportDataSource @Inject constructor(
    private val reportService: ReportService
) {
    suspend fun reportReview(
        reviewId: Int,
        requestReport: RequestReport
    ): ResponseReport = reportService.reportReview(reviewId, requestReport)
}
