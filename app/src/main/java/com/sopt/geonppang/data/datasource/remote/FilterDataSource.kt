package com.sopt.geonppang.data.datasource.remote

import com.sopt.geonppang.data.model.request.RequestFilter
import com.sopt.geonppang.data.model.response.ResponseFilter
import com.sopt.geonppang.data.service.FilterService
import javax.inject.Inject

class FilterDataSource @Inject constructor(
    private val filterService: FilterService,
) {
    suspend fun setFilter(
        requestFilter: RequestFilter
    ): ResponseFilter = filterService.setFilter(requestFilter)
}
