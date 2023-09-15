package com.sopt.geonppang.domain.repository

import com.sopt.geonppang.data.model.request.RequestValidationEmail
import com.sopt.geonppang.data.model.request.RequestValidationNickname

interface ValidationRepository {
    suspend fun validateNickname(requestValidationNickname: RequestValidationNickname) : Result<Unit>
    suspend fun validateEmail(requestValidationEmail: RequestValidationEmail) : Result<Unit>
}
