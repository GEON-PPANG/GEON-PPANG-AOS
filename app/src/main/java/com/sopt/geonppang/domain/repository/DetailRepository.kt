package com.sopt.geonppang.domain.repository

import com.sopt.geonppang.domain.model.BakeryInfo

interface DetailRepository {
    suspend fun fetchDetailBakery(bakeryId: Int): Result<BakeryInfo>
}
