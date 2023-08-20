package com.sopt.geonppang.domain.repository

import com.sopt.geonppang.domain.model.Search

interface SearchRepository {
    suspend fun searchBakery(bakeryTerm: String): Result<Search>
}
