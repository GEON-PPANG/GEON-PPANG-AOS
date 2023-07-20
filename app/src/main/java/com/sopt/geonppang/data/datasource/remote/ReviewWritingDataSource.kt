package com.sopt.geonppang.data.datasource.remote

import com.sopt.geonppang.data.model.request.RequestReviewWriting
import com.sopt.geonppang.data.model.response.ResponseReviewWriting
import com.sopt.geonppang.data.service.ReviewWritingService
import javax.inject.Inject

class ReviewWritingDataSource @Inject constructor(
    private val reviewWritingService: ReviewWritingService,
) {
    suspend fun writeReview(
        bakeryId: Int,
        requestReviewWriting: RequestReviewWriting
    ): ResponseReviewWriting =
        reviewWritingService.writeReview(bakeryId, requestReviewWriting)
}
