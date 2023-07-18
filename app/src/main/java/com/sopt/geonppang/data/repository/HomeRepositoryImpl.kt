package com.sopt.geonppang.data.repository

import com.sopt.geonppang.data.datasource.remote.HomeDataSource
import com.sopt.geonppang.domain.model.BestBakery
import com.sopt.geonppang.domain.model.BestReview
import com.sopt.geonppang.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeDataSource: HomeDataSource,
) : HomeRepository {
    override suspend fun fetchBestBakery(): Result<List<BestBakery>> = runCatching {
        homeDataSource.fetchBestBakery().toBestBakery()
    }

    override suspend fun fetchBestReview(): Result<List<BestReview>> = runCatching {
        homeDataSource.fetchBestReview().toBestReview()
    }
}
