package com.sopt.geonppang.presentation.mypage

import androidx.lifecycle.ViewModel
import com.sopt.geonppang.presentation.dummy.Profile

class MyPageViewModel : ViewModel() {
    private val _profile = Profile(
        memberNickname = "안빵이들",
        mainPurpose = "맛 . 다이어트",
        breadType = Profile.BreadType(
            isGlutenFree = true,
            isVegan = true,
            isNutFree = false,
            isSugarFree = true
        )
    )
    val profile = _profile
}
