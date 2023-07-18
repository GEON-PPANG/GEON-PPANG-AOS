package com.sopt.geonppang.data.model.response

import com.sopt.geonppang.domain.model.Bakery
import com.sopt.geonppang.domain.model.BreadType
import kotlinx.serialization.Serializable

@Serializable
data class ResponseBakeryList(
    val code: Int,
    val data: List<Data>,
    val message: String,
) {
    @Serializable
    data class Data(
        val bakeryId: Int,
        val bakeryName: String,
        val bakeryPicture: String,
        val bookMarkCount: Int,
        val breadType: BreadType,
        val firstNearStation: String,
        val isBookMarked: Boolean,
        val isHACCP: Boolean,
        val isNonGMO: Boolean,
        val isVegan: Boolean,
        val reviewCount: Int,
        val secondNearStation: String,
    ) {
        @Serializable
        data class BreadType(
            val breadTypeId: Int,
            val breadTypeName: String,
            val isGlutenFree: Boolean,
            val isNutFree: Boolean,
            val isSugarFree: Boolean,
            val isVegan: Boolean,
        )
    }

    fun toBakery() = data.map { bakery ->
        Bakery(
            bakeryId = bakery.bakeryId,
            bakeryName = bakery.bakeryName,
            bakeryPicture = bakery.bakeryPicture,
            bookmarkCount = bakery.bookMarkCount,
            firstNearStation = bakery.firstNearStation,
            secondNearStation = bakery.secondNearStation,
            isNonGMO = bakery.isNonGMO,
            isVegan = bakery.isVegan,
            isHACCP = bakery.isHACCP,
            breadType = BreadType(
                bakery.breadType.breadTypeId,
                bakery.breadType.breadTypeName,
                bakery.breadType.isGlutenFree,
                bakery.breadType.isVegan,
                bakery.breadType.isNutFree,
                bakery.breadType.isSugarFree,
            )
        )
    }
}
