package com.sopt.geonppang.data.datasource.remote

import com.sopt.geonppang.data.model.request.RequestSignup
import com.sopt.geonppang.data.model.response.ResponseLogout
import com.sopt.geonppang.data.model.response.ResponseSignup
import com.sopt.geonppang.data.model.response.ResponseWithdraw
import com.sopt.geonppang.data.service.AuthService
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val authService: AuthService,
) {
    suspend fun postSignup(requestSignup: RequestSignup, token: String): ResponseSignup =
        authService.postSignup(requestSignup, token)

    suspend fun withdraw(): ResponseWithdraw = authService.withdraw()
    suspend fun logout(): ResponseLogout = authService.logout()
}
