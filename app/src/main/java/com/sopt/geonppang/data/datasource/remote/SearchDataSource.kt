package com.sopt.geonppang.data.datasource.remote

import com.sopt.geonppang.data.model.response.ResponseSearch
import com.sopt.geonppang.data.service.SearchService
import javax.inject.Inject

class SearchDataSource @Inject constructor(
    private val searchService: SearchService,
) {
    suspend fun searchBakery(bakeryName: String): ResponseSearch =
        searchService.searchBakery(bakeryName)
}
