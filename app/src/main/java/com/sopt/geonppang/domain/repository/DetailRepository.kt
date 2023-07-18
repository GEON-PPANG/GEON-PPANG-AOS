package com.sopt.geonppang.domain.repository

import com.sopt.geonppang.domain.model.BakeryInfo
import com.sopt.geonppang.domain.model.ReviewData

interface DetailRepository {
    suspend fun fetchDetailBakery(bakeryId: Int): Result<BakeryInfo>
    suspend fun fetchDetailReview(bakeryId: Int): Result<ReviewData>
}
