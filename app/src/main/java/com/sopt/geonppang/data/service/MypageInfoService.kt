package com.sopt.geonppang.data.service

import com.sopt.geonppang.data.model.response.ResponseMypageInfo
import retrofit2.http.GET

interface MypageInfoService {
    @GET("/member")
    suspend fun fetchMypageInfo() : ResponseMypageInfo
}