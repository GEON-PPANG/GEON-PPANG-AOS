package com.sopt.geonppang.data.service

import com.sopt.geonppang.data.model.response.ResponseMypageBookmark
import com.sopt.geonppang.data.model.response.ResponseMypageInfo
import com.sopt.geonppang.data.model.response.ResponseMypageReview
import retrofit2.http.GET

interface MypageService {
    @GET("/member")
    suspend fun fetchMypageInfo(): ResponseMypageInfo

    @GET("member/bookMarks")
    suspend fun fetchMypageBookmark(): ResponseMypageBookmark

    @GET("member/reviews")
    suspend fun fethcMypageReveiw(): ResponseMypageReview
}
