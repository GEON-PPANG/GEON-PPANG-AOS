package com.sopt.geonppang.domain.model

data class Review(
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
