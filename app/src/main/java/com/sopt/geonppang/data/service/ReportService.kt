package com.sopt.geonppang.data.service

import com.sopt.geonppang.data.model.request.RequestReport
import com.sopt.geonppang.data.model.response.ResponseReport
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ReportService {
    @POST("report/review/{reviewId}")
    suspend fun reportReview(
        @Path("reviewId") reviewId: Int,
        @Body requestReport: RequestReport
    ): ResponseReport
}
