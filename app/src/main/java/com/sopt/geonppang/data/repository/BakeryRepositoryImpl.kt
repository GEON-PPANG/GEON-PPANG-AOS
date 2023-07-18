package com.sopt.geonppang.data.repository

import com.sopt.geonppang.data.datasource.remote.BakeryDataSource
import com.sopt.geonppang.domain.model.Bakery
import com.sopt.geonppang.domain.repository.BakeryRepository
import javax.inject.Inject

class BakeryRepositoryImpl @Inject constructor(
    private val bakeryDataSource: BakeryDataSource,
) : BakeryRepository {
    override suspend fun fetchBakeryList(
        sortType: String,
        isHard: Boolean,
        isDessert: Boolean,
        isBrunch: Boolean,
    ): Result<List<Bakery>> = runCatching {
        bakeryDataSource.fetchBakeryList(sortType, isHard, isDessert, isBrunch).toBakery()
    }
}
