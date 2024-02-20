package com.sopt.geonppang.data.model.response

import com.sopt.geonppang.domain.model.BakeryInformation
import com.sopt.geonppang.presentation.type.BreadFilterType
import kotlinx.serialization.Serializable

@Serializable
data class ResponseBakeryList(
    val code: Int,
    val data: Data,
    val message: String
) {
    @Serializable
    data class Data(
        val content: List<Content>,
        val empty: Boolean,
        val first: Boolean,
        val last: Boolean,
        val number: Int,
        val numberOfElements: Int,
        val pageable: Pageable,
        val size: Int,
        val sort: Sort,
        val totalElements: Int,
        val totalPages: Int
    ) {
        @Serializable
        data class Content(
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
            val secondNearStation: String
        )

        @Serializable
        data class Pageable(
            val offset: Int,
            val pageNumber: Int,
            val pageSize: Int,
            val paged: Boolean,
            val sort: Sort,
            val unpaged: Boolean
        ) {
            @Serializable
            data class Sort(
                val empty: Boolean,
                val sorted: Boolean,
                val unsorted: Boolean
            )
        }

        @Serializable
        data class Sort(
            val empty: Boolean,
            val sorted: Boolean,
            val unsorted: Boolean
        )
    }

    fun toBakery() = data.content.map { bakery ->
        BakeryInformation(
            bakeryId = bakery.bakeryId,
            bakeryName = bakery.bakeryName,
            bakeryPicture = bakery.bakeryPicture,
            bookmarkCount = bakery.bookMarkCount,
            firstNearStation = bakery.firstNearStation,
            secondNearStation = bakery.secondNearStation,
            isNonGMO = bakery.isNonGMO,
            isVegan = bakery.isVegan,
            isHACCP = bakery.isHACCP,
            breadTypeList = bakery.breadTypeList.mapNotNull { breadTypeIdDto ->
                BreadFilterType.values().find {
                    it.id == breadTypeIdDto.breadTypeId
                }
            }
        )
    }
}
