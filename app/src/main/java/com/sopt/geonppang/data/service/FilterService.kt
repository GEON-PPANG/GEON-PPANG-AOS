package com.sopt.geonppang.data.service

import com.sopt.geonppang.data.model.request.RequestFilter
import com.sopt.geonppang.data.model.response.ResponseFilter
import retrofit2.http.Body
import retrofit2.http.POST

interface FilterService {
    @POST("member/types")
    suspend fun setUserFilter(
        @Body requestFilter: RequestFilter
    ): ResponseFilter
}
