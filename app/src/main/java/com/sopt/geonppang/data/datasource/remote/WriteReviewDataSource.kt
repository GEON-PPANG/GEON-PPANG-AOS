package com.sopt.geonppang.data.datasource.remote

import com.sopt.geonppang.data.model.request.RequestWriteReview
import com.sopt.geonppang.data.model.response.ResponseWriteReview
import com.sopt.geonppang.data.service.WriteReviewService
import javax.inject.Inject

class WriteReviewDataSource @Inject constructor(
    private val writeReviewService: WriteReviewService,
) {
    suspend fun writeReview(
        bakeryId: Int,
        requestWriteReview: RequestWriteReview
    ): ResponseWriteReview =
        writeReviewService.writeReview(bakeryId, requestWriteReview)
}