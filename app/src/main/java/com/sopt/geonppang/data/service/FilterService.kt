package com.sopt.geonppang.data.service

import com.sopt.geonppang.data.model.request.RequestSettingFilter
import com.sopt.geonppang.data.model.response.ResponseSettingFilter
import com.sopt.geonppang.data.model.response.ResponseGetUserFilter
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FilterService {
    @POST("member/types")
    suspend fun setUserFilter(
        @Body requestSettingFilter: RequestSettingFilter
    ): ResponseSettingFilter

    @GET("member/types")
    suspend fun getUserFilter(): ResponseGetUserFilter
}
