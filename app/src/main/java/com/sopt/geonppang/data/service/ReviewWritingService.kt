package com.sopt.geonppang.data.service

import com.sopt.geonppang.data.model.request.RequestReviewWriting
import com.sopt.geonppang.data.model.response.ResponseReviewWriting
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewWritingService {
    @POST("reviews/{bakeryId}")
    suspend fun writeReview(
        @Path("bakeryId") bakeryId: Int,
        @Body requestReviewWriting: RequestReviewWriting,
    ): ResponseReviewWriting
}
