package com.sopt.geonppang.data.model.response

import com.sopt.geonppang.domain.model.Bakery
import com.sopt.geonppang.domain.model.BreadType
import com.sopt.geonppang.domain.model.Search
import kotlinx.serialization.Serializable

@Serializable
data class ResponseSearch(
    val code: Int,
    val data: Data,
    val message: String,
) {
    @Serializable
    data class Data(
        val bakeryList: List<Bakery>,
        val resultCount: Int,
    ) {
        @Serializable
        data class Bakery(
            val bakeryId: Int,
            val bakeryName: String,
            val bakeryPicture: String,
            val bookMarkCount: Int,
            val breadType: BreadType,
            val firstNearStation: String,
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

        fun toSearch() = Search(
            resultCount = resultCount,
            bakeryList = bakeryList.map { searchBakery ->
                Bakery(
                    bakeryId = searchBakery.bakeryId,
                    bakeryName = searchBakery.bakeryName,
                    bakeryPicture = searchBakery.bakeryPicture,
                    bookmarkCount = searchBakery.bookMarkCount,
                    firstNearStation = searchBakery.firstNearStation,
                    secondNearStation = searchBakery.secondNearStation,
                    isNonGMO = searchBakery.isNonGMO,
                    isVegan = searchBakery.isVegan,
                    isHACCP = searchBakery.isHACCP,
                    breadType = BreadType(
                        searchBakery.breadType.breadTypeId,
                        searchBakery.breadType.breadTypeName,
                        searchBakery.breadType.isGlutenFree,
                        searchBakery.breadType.isVegan,
                        searchBakery.breadType.isNutFree,
                        searchBakery.breadType.isSugarFree,
                    )
                )
            }
        )
    }
}
