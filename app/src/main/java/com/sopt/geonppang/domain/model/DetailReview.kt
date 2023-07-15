package com.sopt.geonppang.domain.model

data class DetailReview(
    val reviewText: String,
    val memberNickname: String,
    val createdAt: String,
    val recommendKeywordList: List<RecommendKeyword>
) {
    data class RecommendKeyword(
        val recommendKeywordId: Int,
        val recommendKeywordName: String
    )
}

