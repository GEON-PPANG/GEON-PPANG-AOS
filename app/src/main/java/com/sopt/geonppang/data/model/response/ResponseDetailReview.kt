package com.sopt.geonppang.data.model.response

import com.sopt.geonppang.domain.model.DetailReview
import com.sopt.geonppang.domain.model.ReviewData
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDetailReview(
    val code: Int,
    val data: Data,
    val message: String
) {
    @Serializable
    data class Data(
        val kindPercent: Double,
        val reviewList: List<Review>,
        val specialPercent: Double,
        val deliciousPercent: Double,
        val totalReviewCount: Int,
        val zeroWastePercent: Double
    ) {
        @Serializable
        data class Review(
            val createdAt: String,
            val memberNickname: String,
            val recommendKeywordList: List<RecommendKeyword>,
            val reviewId: Int,
            val reviewText: String
        ) {
            @Serializable
            data class RecommendKeyword(
                val recommendKeywordId: Int,
                val recommendKeywordName: String
            )

            fun toRecommentKeyword() = recommendKeywordList.map { recommendKeyword ->
                DetailReview.RecommendKeyword(
                    recommendKeywordId = recommendKeyword.recommendKeywordId,
                    recommendKeywordName = recommendKeyword.recommendKeywordName
                )
            }
        }

        fun toReviewData() = ReviewData(
            deliciousPercent = (deliciousPercent * 100).toInt(),
            specialPercent = (specialPercent * 100).toInt(),
            kindPercent = (kindPercent * 100).toInt(),
            zeroWastePercent = (zeroWastePercent * 100).toInt(),
            totalReviewCount = totalReviewCount,
            detailReviewList = reviewList.map { review ->
                DetailReview(
                    createdAt = review.createdAt,
                    memberNickname = review.memberNickname,
                    reviewId = review.reviewId,
                    reviewText = review.reviewText,
                    recommendKeywordList = review.toRecommentKeyword()
                )
            }
        )
    }
}
