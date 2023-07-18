package com.sopt.geonppang.data.repository

import com.sopt.geonppang.data.datasource.remote.SearchDataSource
import com.sopt.geonppang.domain.model.Bakery
import com.sopt.geonppang.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchDataSource: SearchDataSource,
) : SearchRepository {
    override suspend fun searchBakery(bakeryName: String): Result<List<Bakery>> =
        kotlin.runCatching { searchDataSource.searchBakery(bakeryName).data.toSearchBakery() }
}
