package com.sopt.geonppang.data.model.response

import com.sopt.geonppang.domain.model.BestReview
import kotlinx.serialization.Serializable

@Serializable
data class ResponseBestReview(
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
        val firstMaxRecommendKeyword: String,
        val firstNearStation: String,
        val isBooked: Boolean,
        val isHACCP: Boolean,
        val isNonGMO: Boolean,
        val isVegan: Boolean,
        val reviewCount: Int,
        val reviewText: String,
        val secondMaxRecommendKeyword: String,
        val secondNearStation: String,
    )

    fun toBestReview() = data.map { bestReview ->
        BestReview(
            bakeryId = bestReview.bakeryId,
            bakeryName = bestReview.bakeryName,
            bakeryImage = bestReview.bakeryPicture,
            bookmarkCount = bestReview.bookMarkCount,
            reviewCount = bestReview.reviewCount,
            reviewText = bestReview.reviewText,
            firstReviewChip = bestReview.firstMaxRecommendKeyword,
            secondReviewChip = bestReview.secondMaxRecommendKeyword
        )
    }
}
