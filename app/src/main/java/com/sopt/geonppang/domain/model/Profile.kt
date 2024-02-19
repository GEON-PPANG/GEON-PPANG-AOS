package com.sopt.geonppang.domain.model

import com.sopt.geonppang.presentation.type.BreadFilterType

data class Profile(
    val memberNickname: String,
    val mainPurpose: String,
    val breadTypeList: List<BreadFilterType>
)
