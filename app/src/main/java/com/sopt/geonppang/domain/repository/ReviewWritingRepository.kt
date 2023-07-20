package com.sopt.geonppang.domain.repository

import com.sopt.geonppang.data.model.request.RequestReviewWriting
import com.sopt.geonppang.data.model.response.ResponseReviewWriting

interface ReviewWritingRepository {
    suspend fun writeReview(
        bakeryIn: Int,
        requestReviewWriting: RequestReviewWriting
    ): Result<ResponseReviewWriting>
}
