package com.sopt.geonppang.domain.repository

import com.sopt.geonppang.data.model.request.RequestValidationEmail
import com.sopt.geonppang.data.model.request.RequestValidationNickname
import com.sopt.geonppang.data.model.response.ResponseValidation

interface ValidationRepository {
    suspend fun validateNickname(requestValidationNickname: RequestValidationNickname): Result<ResponseValidation>
    suspend fun validateEmail(requestValidationEmail: RequestValidationEmail): Result<ResponseValidation>
}
