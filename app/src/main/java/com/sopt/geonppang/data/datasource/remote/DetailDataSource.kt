package com.sopt.geonppang.data.datasource.remote

import com.sopt.geonppang.data.model.response.ResponseDetailBakery
import com.sopt.geonppang.data.service.DetailService
import javax.inject.Inject

class DetailDataSource @Inject constructor(
    private val detailService: DetailService,
) {
    suspend fun fetchDetailBakery(bakeryId: Int): ResponseDetailBakery =
        detailService.fetchDetailBakery(bakeryId)
}
