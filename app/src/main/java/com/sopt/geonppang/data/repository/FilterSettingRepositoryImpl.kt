package com.sopt.geonppang.data.repository

import com.sopt.geonppang.data.datasource.remote.FilterSettingDataSource
import com.sopt.geonppang.data.model.request.RequestSettingFilter
import com.sopt.geonppang.domain.model.SelectedFilter
import com.sopt.geonppang.domain.repository.FilterSettingRepository
import javax.inject.Inject

class FilterSettingRepositoryImpl @Inject constructor(
    private val filterSettingDataSource: FilterSettingDataSource
) : FilterSettingRepository {
    override suspend fun setUserFilter(requestSettingFilter: RequestSettingFilter): Result<SelectedFilter> =
        runCatching {
            filterSettingDataSource.setUserFilter(requestSettingFilter).toSelectedFilter()
        }
}
