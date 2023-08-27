package com.sopt.geonppang.data.repository

import com.sopt.geonppang.data.datasource.remote.GetUserFilterDataSource
import com.sopt.geonppang.domain.model.UserFilter
import com.sopt.geonppang.domain.repository.GetUserFilterRepository
import javax.inject.Inject

class GetUserFilterRepositoryImpl @Inject constructor(
    private val getUserFilterDataSource: GetUserFilterDataSource
) : GetUserFilterRepository {
    override suspend fun getUserFilter(): Result<UserFilter> = runCatching {
        getUserFilterDataSource.getUserFilter().toUserFilter()
    }
}
