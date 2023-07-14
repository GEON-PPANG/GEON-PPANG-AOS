package com.sopt.geonppang.domain.model

data class ReviewData(
    val taste: Int,
    val special: Int,
    val kind: Int,
    val zero: Int,
    val reviewCount: Int,
    val reviewList: List<Review>
)
