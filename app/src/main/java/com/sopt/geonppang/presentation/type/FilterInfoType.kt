package com.sopt.geonppang.presentation.type

enum class FilterInfoType(
    val activityName: String,
    val currentPageRes: Int,
    val maxPageRes: Int,
) {
    ONBOARDING(
        "onBoarding",
        3,
        6,
    ),
    HOME(
        "home",
        1,
        3,
    ),
    MYPAGE(
        "myPage",
        1,
        3,
    )
}
