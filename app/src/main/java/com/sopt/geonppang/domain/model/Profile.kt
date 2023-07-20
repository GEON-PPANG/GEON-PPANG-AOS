package com.sopt.geonppang.domain.model

data class Profile(
    val memberNickname: String,
    val mainPurpose: String,
    val breadType: BreadType
) {
    constructor() : this("", "", BreadType(0,"",false,false,false,false))
}
