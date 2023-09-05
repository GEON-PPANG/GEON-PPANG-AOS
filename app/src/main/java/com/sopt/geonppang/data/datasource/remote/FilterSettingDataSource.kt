package com.sopt.geonppang.data.datasource.remote

import com.sopt.geonppang.data.model.request.RequestSettingFilter
import com.sopt.geonppang.data.model.response.ResponseSettingFilter
import com.sopt.geonppang.data.service.FilterService
import javax.inject.Inject

class FilterSettingDataSource @Inject constructor(
    private val filterService: FilterService,
) {
    suspend fun setUserFilter(
        requestSettingFilter: RequestSettingFilter
    ): ResponseSettingFilter = filterService.setUserFilter(requestSettingFilter)
}
