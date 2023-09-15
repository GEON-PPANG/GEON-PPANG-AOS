package com.sopt.geonppang.data.service

import com.sopt.geonppang.data.model.request.RequestValidationEmail
import com.sopt.geonppang.data.model.request.RequestValidationNickname
import retrofit2.http.Body
import retrofit2.http.POST

interface ValidationService {
    @POST("validation/nickname")
    suspend fun nickname(
        @Body requestValidationNickname: RequestValidationNickname
    )
    @POST("validation/email")
    suspend fun email(
        @Body requestValidationEmail: RequestValidationEmail
    )
}