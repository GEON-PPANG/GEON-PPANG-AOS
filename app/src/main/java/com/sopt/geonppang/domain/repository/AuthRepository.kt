package com.sopt.geonppang.domain.repository

import com.sopt.geonppang.data.model.response.ResponseWithdraw

interface AuthRepository {
    suspend fun withdraw(): Result<ResponseWithdraw>
}
