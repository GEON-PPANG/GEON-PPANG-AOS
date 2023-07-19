package com.sopt.geonppang.data.model.response

import com.sopt.geonppang.domain.model.Bakery
import com.sopt.geonppang.domain.model.BreadType
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMypageBookmark(
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
        val isBookMarked: Boolean,
        val bookMarkCount: Int,
        val reviewCount: Int,
        val breadType: BreadType
    ) {
        @Serializable
        data class BreadType(
            val breadTypeId: Int,
            val breadTypeName: String,
            val isGlutenFree: Boolean,
            val isVegan: Boolean,
            val isNutFree: Boolean,
            val isSugarFree: Boolean,
        )
    }

    fun toMypageBookmark() = data.map { myBookmark ->
        Bakery(
            bakeryId = myBookmark.bakeryId,
            bakeryName = myBookmark.bakeryName,
            bakeryPicture = myBookmark.bakeryPicture,
            bookmarkCount = myBookmark.bookMarkCount,
            firstNearStation = myBookmark.firstNearStation,
            secondNearStation = myBookmark.secondNearStation,
            isNonGMO = myBookmark.isNonGMO,
            isVegan = myBookmark.isVegan,
            isHACCP = myBookmark.isHACCP,
            breadType = BreadType(
                myBookmark.breadType.breadTypeId,
                myBookmark.breadType.breadTypeName,
                myBookmark.breadType.isGlutenFree,
                myBookmark.breadType.isVegan,
                myBookmark.breadType.isNutFree,
                myBookmark.breadType.isSugarFree,
            )
        )
    }
}
