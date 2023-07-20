package com.sopt.geonppang.data.repository

import com.sopt.geonppang.data.datasource.remote.ReviewWritingDataSource
import com.sopt.geonppang.data.model.request.RequestReviewWriting
import com.sopt.geonppang.data.model.response.ResponseReviewWriting
import com.sopt.geonppang.domain.repository.ReviewWritingRepository
import javax.inject.Inject

class ReviewWritingWritingRepositoryImpl @Inject constructor(
    private val reviewWritingDataSource: ReviewWritingDataSource,
) : ReviewWritingRepository {
    override suspend fun writeReview(
        bakeryId: Int,
        requestReviewWriting: RequestReviewWriting
    ): Result<ResponseReviewWriting> = runCatching {
        reviewWritingDataSource.writeReview(bakeryId, requestReviewWriting)
    }
}
