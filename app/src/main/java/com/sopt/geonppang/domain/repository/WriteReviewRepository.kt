package com.sopt.geonppang.domain.repository

import com.sopt.geonppang.data.model.request.RequestWriteReview
import com.sopt.geonppang.data.model.response.ResponseWriteReview

interface WriteReviewRepository {
    suspend fun writeReview(
        bakeryIn: Int,
        requestWriteReview: RequestWriteReview
    ): Result<ResponseWriteReview>
}