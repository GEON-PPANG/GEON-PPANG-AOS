package com.sopt.geonppang.domain.repository

import com.sopt.geonppang.domain.model.Bakery

interface SearchRepository {
    suspend fun searchBakery(bakeryName: String): Result<List<Bakery>>
}
