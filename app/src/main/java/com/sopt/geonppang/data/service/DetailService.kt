package com.sopt.geonppang.data.service

import com.sopt.geonppang.data.model.request.RequestBookMark
import com.sopt.geonppang.data.model.response.ResponseBookMark
import com.sopt.geonppang.data.model.response.ResponseDetailBakery
import com.sopt.geonppang.data.model.response.ResponseDetailReview
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DetailService {
    @GET("bakeries/{bakeryId}")
    suspend fun fetchDetailBakery(
        @Path("bakeryId") bakeryId: Int
    ): ResponseDetailBakery

    @GET("bakeries/{bakeryId}/reviews")
    suspend fun fetchDetailReview(
        @Path("bakeryId") bakeryId: Int
    ): ResponseDetailReview

    @POST("bookMarks/{bakeryId}")
    suspend fun doBookMark(
        @Path("bakeryId") bakeryId: Int,
        @Body requestBookMark: RequestBookMark
    ): ResponseBookMark
}
