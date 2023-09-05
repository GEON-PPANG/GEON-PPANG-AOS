package com.sopt.geonppang.data.repository

import com.sopt.geonppang.data.datasource.remote.ReportDataSource
import com.sopt.geonppang.data.model.request.RequestReport
import com.sopt.geonppang.data.model.response.ResponseReport
import com.sopt.geonppang.domain.repository.ReportRepository
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val reportDataSource: ReportDataSource,
) : ReportRepository {
    override suspend fun reportReview(
        reviewId: Int,
        requestReport: RequestReport
    ): Result<ResponseReport> =
        runCatching { reportDataSource.reportReview(reviewId, requestReport) }
}
