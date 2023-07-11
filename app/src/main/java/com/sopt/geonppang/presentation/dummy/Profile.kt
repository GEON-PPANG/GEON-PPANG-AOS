package com.sopt.geonppang.presentation.dummy

data class Profile(
    val memberNickname: String,
    val mainPurpose: String,
    val breadType: BreadType
) {
    data class BreadType(
        val isGlutenFree: Boolean,
        val isVegan: Boolean,
        val isNutFree: Boolean,
        val isSugarFree: Boolean
    )
}
