package com.sopt.geonppang.domain.repository

import com.sopt.geonppang.data.model.request.RequestSettingFilter
import com.sopt.geonppang.domain.model.SelectedFilter

interface FilterSettingRepository {
    suspend fun setUserFilter(
        requestSettingFilter: RequestSettingFilter
    ): Result<SelectedFilter>
}
