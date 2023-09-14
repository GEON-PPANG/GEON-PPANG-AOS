package com.sopt.geonppang.data.model.response

import com.sopt.geonppang.domain.model.Bakery
import com.sopt.geonppang.domain.model.BreadType
import com.sopt.geonppang.domain.model.MyReview
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMyPageReview(
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
        val breadType: BreadType,
        val reviewId: Int,
        val createdAt: String,
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
                null,
                myReview.bakeryPicture,
                breadType = BreadType(
                    myReview.breadType.breadTypeId,
                    myReview.breadType.breadTypeName,
                    myReview.breadType.isGlutenFree,
                    myReview.breadType.isVegan,
                    myReview.breadType.isNutFree,
                    myReview.breadType.isSugarFree
                )
            ),
            reviewId = myReview.reviewId,
            date = myReview.createdAt
        )
    }
}
