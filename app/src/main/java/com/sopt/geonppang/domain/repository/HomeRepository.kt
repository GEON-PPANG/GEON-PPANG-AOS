package com.sopt.geonppang.domain.repository

import com.sopt.geonppang.domain.model.BestBakery
import com.sopt.geonppang.domain.model.BestReview

interface HomeRepository {
    suspend fun fetchBestBakery(): Result<List<BestBakery>>

    suspend fun fetchBestReview(): Result<List<BestReview>>
}
