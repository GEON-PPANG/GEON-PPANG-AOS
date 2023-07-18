package com.sopt.geonppang.domain.model

data class ReviewData(
    val tastePercent: Int,
    val specialPercent: Int,
    val kindPercent: Int,
    val zeroPercent: Int,
    val totalReviewCount: Int,
    val detailReviewList: List<DetailReview>
)
