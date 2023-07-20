package com.sopt.geonppang.domain.repository

import com.sopt.geonppang.data.model.request.RequestFilter
import com.sopt.geonppang.domain.model.SelectedFilter

interface FilterRepository {
    suspend fun setFilter(
        requestFilter: RequestFilter
    ): Result<SelectedFilter>
}
