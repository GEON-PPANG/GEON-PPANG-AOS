package com.sopt.geonppang.data.service

import com.sopt.geonppang.data.model.response.ResponseBakeryList
import retrofit2.http.GET
import retrofit2.http.Query

interface BakeryService {
    @GET("bakeries")
    suspend fun fetchBakeryList(
        @Query("sortingOption") sort: String,
        @Query("personalFilter") personal: Boolean,
        @Query("isHard") isHard: Boolean,
        @Query("isDessert") isDessert: Boolean,
        @Query("isBrunch") isBrunch: Boolean,
    ): ResponseBakeryList
}
