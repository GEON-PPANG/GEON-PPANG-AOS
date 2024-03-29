package com.sopt.geonppang.domain.model

import com.sopt.geonppang.presentation.type.BreadFilterType

data class Profile(
    var memberNickname: String = "",
    var mainPurpose: String = "",
    var breadTypeList: List<BreadFilterType> = listOf()
)
