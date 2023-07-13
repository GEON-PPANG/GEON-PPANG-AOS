package com.sopt.geonppang.domain.model

data class BestReview(
    val bakeryId: Int,
    val bakeryName: String,
    val reviewText: String,
    val firstReviewChip: String,
    val secondReviewChip: String,
    val isBooked: Boolean,
    val bookmarkCount: Int,
    // TODO image type -> String 으로 수정
    val bakeryImage: Int,
    val reviewCount: Int,
)
