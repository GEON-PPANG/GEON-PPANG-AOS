package com.sopt.geonppang.data.service

import com.sopt.geonppang.data.model.response.ResponseMyPageBookmark
import com.sopt.geonppang.data.model.response.ResponseMyPageInfo
import com.sopt.geonppang.data.model.response.ResponseMyPageReview
import retrofit2.http.GET

interface MyPageService {
    @GET("/member")
    suspend fun fetchMyPageInfo(): ResponseMyPageInfo

    @GET("member/bookMarks")
    suspend fun fetchMyPageBookmark(): ResponseMyPageBookmark

    @GET("member/reviews")
    suspend fun fethcMyPageReveiw(): ResponseMyPageReview
}
