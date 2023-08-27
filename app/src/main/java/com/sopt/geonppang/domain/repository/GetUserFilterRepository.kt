package com.sopt.geonppang.domain.repository

import com.sopt.geonppang.domain.model.UserFilter

interface GetUserFilterRepository {
    suspend fun getUserFilter(): Result<UserFilter>
}
