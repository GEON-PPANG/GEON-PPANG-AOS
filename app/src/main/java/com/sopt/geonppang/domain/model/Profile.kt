package com.sopt.geonppang.domain.model

import com.sopt.geonppang.presentation.type.BreadFilterType
import com.sopt.geonppang.presentation.type.MainPurposeFilterType

data class Profile(
    var memberNickname: String = "",
    var mainPurpose: String = "",
    var breadTypeList: List<BreadFilterType> = listOf()
)
