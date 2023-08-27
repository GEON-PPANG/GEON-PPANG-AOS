package com.sopt.geonppang.domain.repository

import com.sopt.geonppang.domain.model.MyReviewDetail

interface MyReviewDetailRepository {
    suspend fun fetchMyReviewDetail(reviewId: Int): Result<MyReviewDetail>
}
