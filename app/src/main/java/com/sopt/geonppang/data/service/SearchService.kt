package com.sopt.geonppang.data.service

import com.sopt.geonppang.data.model.response.ResponseSearch
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("search/bakeries")
    suspend fun searchBakery(
        @Query("searchTerm") bakeryName: String,
    ): ResponseSearch
}
