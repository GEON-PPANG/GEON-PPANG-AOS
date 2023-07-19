package com.sopt.geonppang.presentation.type

enum class BakerySortType(
    val sortType: String,
    val sortName: String,
) {
    DEFAULT(
        "default",
        "기본순"
    ),
    REVIEW(
        "review",
        "리뷰 많은 순"
    )
}
