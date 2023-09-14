package com.sopt.geonppang.domain.repository

import com.sopt.geonppang.data.model.response.ResponseLogout
import com.sopt.geonppang.data.model.response.ResponseWithdraw
import retrofit2.Response

interface AuthRepository {
    suspend fun withdraw(): Result<ResponseWithdraw>
    suspend fun logout(): Result<ResponseLogout>
    suspend fun login() : Result<Response<Unit>>
}
