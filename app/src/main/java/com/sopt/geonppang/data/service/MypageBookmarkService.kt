package com.sopt.geonppang.data.service

import com.sopt.geonppang.data.model.response.ResponseMypageBookmark
import retrofit2.http.GET

interface MypageBookmarkService {
    @GET("member/bookMarks")
    suspend fun fetchMypageBookmark():ResponseMypageBookmark
}