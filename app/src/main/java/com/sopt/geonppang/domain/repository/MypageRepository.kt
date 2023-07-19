package com.sopt.geonppang.domain.repository

import com.sopt.geonppang.domain.model.Bakery
import com.sopt.geonppang.domain.model.MyReview

interface MypageRepository {
    suspend fun fetchMyBookmark(): Result<List<Bakery>>
    suspend fun fetchMyReview(): Result<List<MyReview>>
}
