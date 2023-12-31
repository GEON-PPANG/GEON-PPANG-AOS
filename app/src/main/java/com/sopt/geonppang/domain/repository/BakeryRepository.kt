package com.sopt.geonppang.domain.repository

import com.sopt.geonppang.domain.model.Bakery

interface BakeryRepository {
    suspend fun fetchBakeryList(
        sortOption: String,
        personalFilter: Boolean,
        isHard: Boolean,
        isDessert: Boolean,
        isBrunch: Boolean,
    ): Result<List<Bakery>>
}
