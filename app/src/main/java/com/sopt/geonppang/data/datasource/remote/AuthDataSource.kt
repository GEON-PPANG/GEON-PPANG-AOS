package com.sopt.geonppang.data.datasource.remote

import com.sopt.geonppang.data.model.request.RequestLogin
import com.sopt.geonppang.data.model.response.ResponseLogout
import com.sopt.geonppang.data.model.response.ResponseWithdraw
import com.sopt.geonppang.data.service.AuthService
import retrofit2.Response
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val authService: AuthService
) {
    suspend fun withdraw(): ResponseWithdraw = authService.withdraw()
    suspend fun logout(): ResponseLogout = authService.logout()
    suspend fun login(requestLogin: RequestLogin): Response<Unit> = authService.login(requestLogin)
}
