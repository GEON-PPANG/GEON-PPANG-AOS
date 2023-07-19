package com.sopt.geonppang.domain.repository

import com.sopt.geonppang.domain.model.MyReview

interface MypageReviewRepository {
    suspend fun fetchMyReview(): Result<List<MyReview>>
}
