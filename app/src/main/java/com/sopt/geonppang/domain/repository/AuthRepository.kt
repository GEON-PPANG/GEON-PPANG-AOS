package com.sopt.geonppang.domain.repository

import com.sopt.geonppang.data.model.request.RequestSignup
import com.sopt.geonppang.data.model.response.ResponseLogout
import com.sopt.geonppang.data.model.response.ResponseSignup
import com.sopt.geonppang.data.model.response.ResponseWithdraw

interface AuthRepository {
    suspend fun signup(requestSignup: RequestSignup, token: String): Result<ResponseSignup>
    suspend fun withdraw(): Result<ResponseWithdraw>
    suspend fun logout(): Result<ResponseLogout>
}
