package com.sopt.geonppang.data.service

import com.sopt.geonppang.data.model.response.ResponseBakeryList
import retrofit2.http.GET
import retrofit2.http.Query

interface BakeryService {
    @GET("bakeries")
    suspend fun fetchBakeryList(
        @Query("sort") sort: String,
        @Query("isHard") isHard: Boolean,
        @Query("isDessert") isDessert: Boolean,
        @Query("isBrunch") isBrunch: Boolean,
    ): ResponseBakeryList
}