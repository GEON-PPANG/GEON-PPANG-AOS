package com.sopt.geonppang.data.datasource.remote

import com.sopt.geonppang.data.model.response.ResponseGetUserFilter
import com.sopt.geonppang.data.service.FilterService
import javax.inject.Inject

class GetUserFilterDataSource @Inject constructor(
    private val filterService: FilterService
) {
    suspend fun getUserFilter(): ResponseGetUserFilter =
        filterService.getUserFilter()
}
