package com.sopt.geonppang.data.repository

import com.sopt.geonppang.data.datasource.remote.ValidationDataSource
import com.sopt.geonppang.data.model.request.RequestValidationEmail
import com.sopt.geonppang.data.model.request.RequestValidationNickname
import com.sopt.geonppang.domain.repository.ValidationRepository
import javax.inject.Inject

class ValidationRepositoryImpl @Inject constructor(
    private val validateDataSource: ValidationDataSource
) :
    ValidationRepository {
    override suspend fun validateNickname(requestValidationNickname: RequestValidationNickname): Result<Unit> =
        runCatching { validateDataSource.validateNickname(requestValidationNickname) }

    override suspend fun validateEmail(requestValidationEmail: RequestValidationEmail): Result<Unit> =
        runCatching { validateDataSource.validateEmail(requestValidationEmail) }
}