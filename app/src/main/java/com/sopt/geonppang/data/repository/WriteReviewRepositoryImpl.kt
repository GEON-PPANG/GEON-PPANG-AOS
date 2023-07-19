package com.sopt.geonppang.data.repository

import com.sopt.geonppang.data.datasource.remote.WriteReviewDataSource
import com.sopt.geonppang.data.model.request.RequestWriteReview
import com.sopt.geonppang.data.model.response.ResponseWriteReview
import com.sopt.geonppang.domain.repository.WriteReviewRepository
import javax.inject.Inject

class WriteReviewRepositoryImpl @Inject constructor(
    private val writeReviewDataSource: WriteReviewDataSource,
) : WriteReviewRepository {
    override suspend fun writeReview(
        bakeryId: Int,
        requestWriteReview: RequestWriteReview
    ): Result<ResponseWriteReview> = runCatching {
        writeReviewDataSource.writeReview(bakeryId, requestWriteReview)
    }

}