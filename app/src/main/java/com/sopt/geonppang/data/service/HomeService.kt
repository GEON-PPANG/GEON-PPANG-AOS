package com.sopt.geonppang.data.service

import com.sopt.geonppang.data.model.response.ResponseBestBakery
import com.sopt.geonppang.data.model.response.ResponseBestReview
import retrofit2.http.GET

interface HomeService {
    @GET("best/bakeries")
    suspend fun fetchBestBakery(): ResponseBestBakery

    @GET("best/reviews")
    suspend fun fetchBestReview(): ResponseBestReview
}
