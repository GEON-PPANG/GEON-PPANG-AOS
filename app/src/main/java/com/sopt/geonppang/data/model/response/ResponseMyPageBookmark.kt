package com.sopt.geonppang.data.model.response

import com.sopt.geonppang.domain.model.BakeryInformation
import com.sopt.geonppang.presentation.type.BreadFilterType
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMyPageBookmark(
    val code: Int,
    val message: String,
    val data: List<Data>
) {
    @Serializable
    data class Data(
        val bakeryId: Int,
        val bakeryName: String,
        val bakeryPicture: String,
        val isHACCP: Boolean,
        val isVegan: Boolean,
        val isNonGMO: Boolean,
        val firstNearStation: String,
        val secondNearStation: String,
        val bookMarkCount: Int,
        val reviewCount: Int,
        val breadTypeList: List<BreadTypeIdDto>
    )

    fun toMypageBookmark() = data.map { myBookmark ->
        BakeryInformation(
            bakeryId = myBookmark.bakeryId,
            bakeryName = myBookmark.bakeryName,
            bakeryPicture = myBookmark.bakeryPicture,
            bookmarkCount = myBookmark.bookMarkCount,
            firstNearStation = myBookmark.firstNearStation,
            secondNearStation = myBookmark.secondNearStation,
            isNonGMO = myBookmark.isNonGMO,
            isVegan = myBookmark.isVegan,
            isHACCP = myBookmark.isHACCP,
            breadTypeList = myBookmark.breadTypeList.mapNotNull { breadTypeIdDto ->
                BreadFilterType.values().find {
                    it.id == breadTypeIdDto.breadTypeId
                }
            }
        )
    }
}
