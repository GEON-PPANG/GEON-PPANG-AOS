package com.sopt.geonppang.data.service

import com.sopt.geonppang.data.model.request.RequestWriteReview
import com.sopt.geonppang.data.model.response.ResponseWriteReview
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface WriteReviewService  {
    @POST("reviews/{bakeryId}")
    suspend fun writeReview(
        @Path("bakeryId") bakeryId:Int,
        @Body requestWriteReview: RequestWriteReview
    ):ResponseWriteReview
}