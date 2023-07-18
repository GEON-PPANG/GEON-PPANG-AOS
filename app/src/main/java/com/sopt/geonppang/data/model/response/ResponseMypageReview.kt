package com.sopt.geonppang.data.model.response

import com.sopt.geonppang.domain.model.Bakery
import com.sopt.geonppang.domain.model.BreadType
import com.sopt.geonppang.domain.model.MyReview
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMypageReview(
    val code: Int,
    val message: String,
    val data: List<Data>,
) {
    @Serializable
    data class Data(
        val bakeryId: Int,
        val bakeryName: String,
        val bakeryPicture: String,
        @SerialName("isHACCP")
        val isHACCP: Boolean,
        @SerialName("isVegan")
        val isVegan: Boolean,
        @SerialName("isNonGMO")
        val isNonGmo: Boolean,
        val firstNearStation: String,
        val secondNearStation: String,
        val isBookMarked: String?,
        val bookMarkCount: Int?,
        val breadTypeResponseDto: BreadTypeResponseDto,
        val reviewId: Int,
        val createdAt: String,
        ) {
        @Serializable
        data class BreadTypeResponseDto(
            val breadTypeId: Int,
            val breadTypeName: String,
            val isGlutenFree: Boolean,
            val isVegan: Boolean,
            val isNutFree: Boolean,
            val isSugarFree: Boolean,
        )
    }

    fun toMyReview() = data.map { myReview ->
        MyReview(
            bakery = Bakery(
                myReview.bakeryId,
                myReview.bakeryName,
                myReview.isHACCP,
                myReview.isVegan,
                myReview.isNonGmo,
                myReview.firstNearStation,
                myReview.secondNearStation,
                myReview.bookMarkCount,
                myReview.bakeryPicture,
                breadType = BreadType(
                    myReview.breadTypeResponseDto.breadTypeId,
                    myReview.breadTypeResponseDto.breadTypeName,
                    myReview.breadTypeResponseDto.isGlutenFree,
                    myReview.breadTypeResponseDto.isVegan,
                    myReview.breadTypeResponseDto.isNutFree,
                    myReview.breadTypeResponseDto.isSugarFree
                )
            ),
            reviewId = myReview.reviewId,
            date = myReview.createdAt
        )
    }
}
