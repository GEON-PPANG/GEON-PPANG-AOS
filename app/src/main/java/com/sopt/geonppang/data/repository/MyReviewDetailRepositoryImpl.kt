package com.sopt.geonppang.data.repository

import com.sopt.geonppang.data.datasource.remote.MyReviewDetailDataSource
import com.sopt.geonppang.domain.model.MyReviewDetail
import com.sopt.geonppang.domain.repository.MyReviewDetailRepository
import javax.inject.Inject

class MyReviewDetailRepositoryImpl @Inject constructor(
    private val myReviewDetailDataSource: MyReviewDetailDataSource
) : MyReviewDetailRepository {
    override suspend fun fetchMyReviewDetail(reviewId: Int): Result<MyReviewDetail> = runCatching {
        myReviewDetailDataSource.fetchMyReviewDetail(reviewId).toMyReviewDetail()
    }
}
