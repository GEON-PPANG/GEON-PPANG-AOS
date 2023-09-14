package com.sopt.geonppang.domain.repository

import com.sopt.geonppang.data.model.request.RequestLogin
import com.sopt.geonppang.data.model.response.ResponseLogout
import com.sopt.geonppang.data.model.response.ResponseWithdraw
import retrofit2.Response

interface AuthRepository {
    suspend fun withdraw(): Result<ResponseWithdraw>
    suspend fun logout(): Result<ResponseLogout>
    suspend fun login(requestLogin: RequestLogin): Result<Response<Unit>>
}
