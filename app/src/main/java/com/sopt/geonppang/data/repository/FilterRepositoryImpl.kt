package com.sopt.geonppang.data.repository

import com.sopt.geonppang.data.datasource.remote.FilterDataSource
import com.sopt.geonppang.data.model.request.RequestFilter
import com.sopt.geonppang.domain.model.SelectedFilter
import com.sopt.geonppang.domain.repository.FilterRepository
import javax.inject.Inject

class FilterRepositoryImpl @Inject constructor(
    private val filterDataSource: FilterDataSource
) : FilterRepository {
    override suspend fun setUserFilter(requestFilter: RequestFilter): Result<SelectedFilter> =
        runCatching {
            filterDataSource.setUserFilter(requestFilter).toSelectedFilter()
        }
}
