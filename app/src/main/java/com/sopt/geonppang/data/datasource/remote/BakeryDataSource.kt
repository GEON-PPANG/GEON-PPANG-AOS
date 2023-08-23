package com.sopt.geonppang.data.datasource.remote

import com.sopt.geonppang.data.model.response.ResponseBakeryList
import com.sopt.geonppang.data.service.BakeryService
import javax.inject.Inject

class BakeryDataSource @Inject constructor(
    private val bakeryService: BakeryService,
) {
    suspend fun fetchBakeryList(
        sortOption: String,
        personalFilter: Boolean,
        isHard: Boolean,
        isDessert: Boolean,
        isBrunch: Boolean,
    ): ResponseBakeryList =
        bakeryService.fetchBakeryList(sortOption, personalFilter, isHard, isDessert, isBrunch)
}
