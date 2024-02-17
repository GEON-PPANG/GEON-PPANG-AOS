package com.sopt.geonppang.data.model.response

import com.sopt.geonppang.domain.model.BakeryInformation
import com.sopt.geonppang.domain.model.Search
import com.sopt.geonppang.presentation.type.BreadFilterType
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
            val breadTypeList: List<BreadTypeIdDto>,
            val firstNearStation: String,
            val isHACCP: Boolean,
            val isNonGMO: Boolean,
            val isVegan: Boolean,
            val reviewCount: Int,
            val secondNearStation: String,
        )

        fun toSearch() = Search(
            resultCount = resultCount,
            bakeryList = bakeryList.map { searchBakery ->
                BakeryInformation(
                    bakeryId = searchBakery.bakeryId,
                    bakeryName = searchBakery.bakeryName,
                    bakeryPicture = searchBakery.bakeryPicture,
                    bookmarkCount = searchBakery.bookMarkCount,
                    firstNearStation = searchBakery.firstNearStation,
                    secondNearStation = searchBakery.secondNearStation,
                    isNonGMO = searchBakery.isNonGMO,
                    isVegan = searchBakery.isVegan,
                    isHACCP = searchBakery.isHACCP,
                    breadTypeList = searchBakery.breadTypeList.mapNotNull { breadTypeIdDto ->
                        BreadFilterType.values().find {
                            it.id == breadTypeIdDto.breadTypeId
                        }
                    }
                )
            }
        )
    }
}
