package com.sopt.geonppang.presentation.type

enum class FilterInfoType(
    val activityName: String,
    val maxPage: Int,
) {
    ONBOARDING(
        "onBoarding",
        6,
    ),
    HOME(
        "home",
        3,
    ),
    MYPAGE(
        "myPage",
        3,
    )
}
