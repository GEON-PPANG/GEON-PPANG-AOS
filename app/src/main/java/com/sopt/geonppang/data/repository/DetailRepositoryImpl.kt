package com.sopt.geonppang.data.repository

import com.sopt.geonppang.data.datasource.remote.DetailDataSource
import com.sopt.geonppang.domain.model.BakeryInfo
import com.sopt.geonppang.domain.repository.DetailRepository
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val detailDataSource: DetailDataSource,
) : DetailRepository {
    override suspend fun fetchDetailBakery(bakeryId: Int): Result<BakeryInfo> = runCatching {
        detailDataSource.fetchDetailBakery(bakeryId).data.toBakeryInfo()
    }
}
