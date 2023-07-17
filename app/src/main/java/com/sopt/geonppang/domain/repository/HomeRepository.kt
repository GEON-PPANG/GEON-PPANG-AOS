package com.sopt.geonppang.domain.repository

import com.sopt.geonppang.domain.model.BestBakery

interface HomeRepository {
    suspend fun fetchBestBakery(): Result<List<BestBakery>>
}
