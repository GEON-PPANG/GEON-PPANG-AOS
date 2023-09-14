package com.sopt.geonppang.data.service

import com.sopt.geonppang.data.model.request.RequestSignup
import com.sopt.geonppang.data.model.response.ResponseLogout
import com.sopt.geonppang.data.model.response.ResponseSignup
import com.sopt.geonppang.data.model.response.ResponseWithdraw
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("auth/signup")
    suspend fun signup(
        @Body requestSignup: RequestSignup,
        @Header("PlatformAccessToken") token: String,
    ): ResponseSignup

    @DELETE("auth/withdraw")
    suspend fun withdraw(): ResponseWithdraw

    @POST("auth/logout")
    suspend fun logout(): ResponseLogout

    @POST("auth/login")
    suspend fun login() : Response<Unit>
}
