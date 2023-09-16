package com.sopt.geonppang.domain.repository

import com.sopt.geonppang.data.model.request.RequestNicknameSetting
import com.sopt.geonppang.data.model.request.RequestSignup
import com.sopt.geonppang.data.model.response.ResponseLogout
import com.sopt.geonppang.data.model.response.ResponseNickNameSetting
import com.sopt.geonppang.data.model.response.ResponseSignup
import com.sopt.geonppang.data.model.response.ResponseWithdraw
import retrofit2.Response

interface AuthRepository {
    suspend fun signup(
        platformToken: String,
        requestSignup: RequestSignup
    ): Result<Response<ResponseSignup>>

    suspend fun withdraw(): Result<ResponseWithdraw>
    suspend fun logout(): Result<ResponseLogout>
    suspend fun settingNickname(nickName: RequestNicknameSetting): Result<ResponseNickNameSetting>
}
