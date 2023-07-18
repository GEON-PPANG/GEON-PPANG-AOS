package com.sopt.geonppang.data.service

import com.sopt.geonppang.data.model.response.ResponseDetailBakery
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailService {
    @GET("bakeries/{bakeryId}")
    suspend fun fetchDetailBakery(
        @Path("bakeryId") bakeryId: Int
    ): ResponseDetailBakery
}
