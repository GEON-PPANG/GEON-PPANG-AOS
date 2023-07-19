package com.sopt.geonppang.data.service

import com.sopt.geonppang.data.model.response.ResponseMypageReview
import retrofit2.http.GET

interface MypageReviewService {
    @GET("member/reviews")
    suspend fun fethcMypageReveiw(): ResponseMypageReview
}
