package com.sopt.geonppang.domain.repository

import com.sopt.geonppang.domain.model.Profile

interface MypageInfoRepository {
    suspend fun fetchMypageInfo(): Result<Profile>
}