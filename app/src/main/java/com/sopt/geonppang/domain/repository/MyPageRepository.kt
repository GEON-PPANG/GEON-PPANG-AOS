package com.sopt.geonppang.domain.repository

import com.sopt.geonppang.domain.model.Bakery
import com.sopt.geonppang.domain.model.MyReview
import com.sopt.geonppang.domain.model.Profile

interface MyPageRepository {
    suspend fun fetchMypageInfo(): Result<Profile>
    suspend fun fetchMyBookmark(): Result<List<Bakery>>
    suspend fun fetchMyReview(): Result<List<MyReview>>
}
