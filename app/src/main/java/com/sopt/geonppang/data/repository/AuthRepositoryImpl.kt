package com.sopt.geonppang.data.repository

import com.sopt.geonppang.data.datasource.remote.AuthDataSource
import com.sopt.geonppang.data.model.request.RequestSignup
import com.sopt.geonppang.data.model.response.ResponseLogout
import com.sopt.geonppang.data.model.response.ResponseSignup
import com.sopt.geonppang.data.model.response.ResponseWithdraw
import com.sopt.geonppang.domain.repository.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun signup(
        platformToken: String,
        requestSignup: RequestSignup,
    ): Result<Response<ResponseSignup>> =
        runCatching { authDataSource.postSignup(platformToken, requestSignup) }

    override suspend fun withdraw(): Result<ResponseWithdraw> =
        runCatching { authDataSource.withdraw() }

    override suspend fun logout(): Result<ResponseLogout> =
        runCatching { authDataSource.logout() }
}
