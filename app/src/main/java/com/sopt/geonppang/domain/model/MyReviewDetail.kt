package com.sopt.geonppang.domain.model

data class MyReviewDetail(
    val recommendKeywordList: List<DetailReview.RecommendKeyword>,
    val reviewText: String,
    val isLike: Boolean
)
