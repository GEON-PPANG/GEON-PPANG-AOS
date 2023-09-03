package com.sopt.geonppang.data.service

import com.sopt.geonppang.data.model.response.ResponseWithdraw
import retrofit2.http.DELETE

interface AuthService {
    @DELETE("auth/withdraw")
    suspend fun withdraw(): ResponseWithdraw
}
