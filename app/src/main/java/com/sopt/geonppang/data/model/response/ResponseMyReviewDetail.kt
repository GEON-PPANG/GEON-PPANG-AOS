package com.sopt.geonppang.data.model.response

import com.sopt.geonppang.domain.model.DetailReview
import com.sopt.geonppang.domain.model.MyReviewDetail
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMyReviewDetail(
    val code: Int,
    val data: Data,
    val message: String
) {
    @Serializable
    data class Data(
        val isLike: Boolean,
        val recommendKeywordList: List<RecommendKeyword>,
        val reviewId: Int,
        val reviewText: String
    ) {
        @Serializable
        data class RecommendKeyword(
            val recommendKeywordId: Int,
            val recommendKeywordName: String
        )
    }

    fun toMyReviewDetail() = MyReviewDetail(
        reviewText = data.reviewText,
        recommendKeywordList = data.recommendKeywordList.map { recommendKeyword ->
            DetailReview.RecommendKeyword(
                recommendKeywordId = recommendKeyword.recommendKeywordId,
                recommendKeywordName = recommendKeyword.recommendKeywordName
            )
        },
        isLike = data.isLike
    )
}
