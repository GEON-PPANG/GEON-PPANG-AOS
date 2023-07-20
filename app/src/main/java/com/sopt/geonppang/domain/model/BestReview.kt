package com.sopt.geonppang.domain.model

data class BestReview(
    val bakeryId: Int,
    val bakeryName: String,
    val reviewText: String,
    val firstReviewChip: String,
    val secondReviewChip: String?,
    val bookmarkCount: Int,
    val bakeryImage: String,
    val reviewCount: Int,
)
