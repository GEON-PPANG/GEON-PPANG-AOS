package com.sopt.geonppang.data.model.response

import com.sopt.geonppang.domain.model.BakeryInformation
import com.sopt.geonppang.domain.model.MyReview
import com.sopt.geonppang.presentation.type.BreadFilterType
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
        val breadTypeList: List<BreadTypeIdDto>,
        val reviewId: Int,
        val createdAt: String,
    )

    fun toMyReview() = data.map { myReview ->
        MyReview(
            bakery = BakeryInformation(
                myReview.bakeryId,
                myReview.bakeryName,
                myReview.isHACCP,
                myReview.isVegan,
                myReview.isNonGmo,
                myReview.firstNearStation,
                myReview.secondNearStation,
                null,
                myReview.bakeryPicture,
                breadTypeList = myReview.breadTypeList.mapNotNull { breadTypeIdDto ->
                    BreadFilterType.values().find {
                        it.id == breadTypeIdDto.breadTypeId
                    }
                }
            ),
            reviewId = myReview.reviewId,
            date = myReview.createdAt
        )
    }
}
