package com.sopt.geonppang.data.datasource.remote

import com.sopt.geonppang.data.model.response.ResponseWithdraw
import com.sopt.geonppang.data.service.AuthService
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val authService: AuthService
) {
    suspend fun withdraw(): ResponseWithdraw = authService.withdraw()
}
