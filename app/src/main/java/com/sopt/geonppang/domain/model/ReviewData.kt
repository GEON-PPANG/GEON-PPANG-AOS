package com.sopt.geonppang.domain.model

data class ReviewData(
    val deliciousPercent: Int,
    val specialPercent: Int,
    val kindPercent: Int,
    val zeroWastePercent: Int,
    val totalReviewCount: Int,
    val detailReviewList: List<DetailReview>
)
