package com.sopt.geonppang.data.service

import com.sopt.geonppang.data.model.response.ResponseMyReviewDetail
import retrofit2.http.GET
import retrofit2.http.Path

interface MyReviewDetailService {
    @GET("/reviews/{reviewId}")
    suspend fun fetchMyReviewDetail(
        @Path("reviewId") reviewId: Int,
    ): ResponseMyReviewDetail
}
