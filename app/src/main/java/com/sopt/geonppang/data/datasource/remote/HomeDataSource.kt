package com.sopt.geonppang.data.datasource.remote

import com.sopt.geonppang.data.model.response.ResponseBestBakery
import com.sopt.geonppang.data.model.response.ResponseBestReview
import com.sopt.geonppang.data.service.HomeService
import javax.inject.Inject

class HomeDataSource @Inject constructor(
    private val homeService: HomeService,
) {
    suspend fun fetchBestBakery(): ResponseBestBakery =
        homeService.fetchBestBakery()
}
