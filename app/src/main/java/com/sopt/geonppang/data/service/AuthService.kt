package com.sopt.geonppang.data.service

import com.sopt.geonppang.data.model.request.RequestLogin
import com.sopt.geonppang.data.model.request.RequestSignup
import com.sopt.geonppang.data.model.response.ResponseLogout
import com.sopt.geonppang.data.model.response.ResponseNickNameSetting
import com.sopt.geonppang.data.model.response.ResponseSignup
import com.sopt.geonppang.data.model.response.ResponseWithdraw
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("auth/signup")
    suspend fun postSignup(
        @Header("Platform-token") token: String,
        @Body requestSignup: RequestSignup,
    ): Response<ResponseSignup>

    @DELETE("auth/withdraw")
    suspend fun withdraw(): ResponseWithdraw

    @POST("auth/logout")
    suspend fun logout(): ResponseLogout

    @POST("auth/login")
    suspend fun login(
        @Body requestLogin: RequestLogin
    ): Response<Unit>

    @POST("member/nickname")
    suspend fun settingNickName(
        @Body requestNicknameSetting: RequestNicknameSetting
    ): ResponseNickNameSetting
}
