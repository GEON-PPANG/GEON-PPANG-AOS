package com.sopt.geonppang.data.datasource.remote

import com.sopt.geonppang.data.model.request.RequestNicknameSetting
import com.sopt.geonppang.data.model.request.RequestSignup
import com.sopt.geonppang.data.model.response.ResponseLogout
import com.sopt.geonppang.data.model.response.ResponseNickNameSetting
import com.sopt.geonppang.data.model.response.ResponseSignup
import com.sopt.geonppang.data.model.response.ResponseWithdraw
import com.sopt.geonppang.data.service.AuthService
import retrofit2.Response
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val authService: AuthService,
) {
    suspend fun postSignup(
        platformToken: String,
        requestSignup: RequestSignup
    ): Response<ResponseSignup> =
        authService.postSignup(platformToken, requestSignup)

    suspend fun withdraw(): ResponseWithdraw = authService.withdraw()
    suspend fun logout(): ResponseLogout = authService.logout()
    suspend fun settingNickname(nickname: RequestNicknameSetting): ResponseNickNameSetting =
        authService.settingNickName(nickname)
}
