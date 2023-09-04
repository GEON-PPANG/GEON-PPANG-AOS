package com.sopt.geonppang.data.service

import com.sopt.geonppang.data.model.response.ResponseLogout
import com.sopt.geonppang.data.model.response.ResponseWithdraw
import retrofit2.http.DELETE
import retrofit2.http.POST

interface AuthService {
    @DELETE("auth/withdraw")
    suspend fun withdraw(): ResponseWithdraw

    @POST("auth/logout")
    suspend fun logout(): ResponseLogout
}
