package com.sopt.geonppang.domain.repository

import com.sopt.geonppang.domain.model.BakeryInformation
import com.sopt.geonppang.domain.model.MyReview
import com.sopt.geonppang.domain.model.Profile

interface MyPageRepository {
    suspend fun fetchProfileInfo(): Result<Profile>
    suspend fun fetchMyBookmark(): Result<List<BakeryInformation>>
    suspend fun fetchMyReview(): Result<List<MyReview>>
}
