package com.sopt.geonppang.domain.repository

import com.sopt.geonppang.data.model.request.RequestReport
import com.sopt.geonppang.data.model.response.ResponseReport

interface ReportRepository {
    suspend fun reportReview(reviewId: Int, requestReport: RequestReport): Result<ResponseReport>
}

