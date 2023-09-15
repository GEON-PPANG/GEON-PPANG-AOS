package com.sopt.geonppang.data.datasource.remote

import com.sopt.geonppang.data.model.request.RequestValidationEmail
import com.sopt.geonppang.data.model.request.RequestValidationNickname
import com.sopt.geonppang.data.service.ValidationService
import javax.inject.Inject

class ValidationDataSource @Inject constructor(
    private val validationService: ValidationService
) {
    suspend fun validateNickname(requestValidationNickname: RequestValidationNickname): Unit =
        validationService.nickname(requestValidationNickname)

    suspend fun validateEmail(requestValidationEmail: RequestValidationEmail) =
        validationService.email(requestValidationEmail)
}
