package com.sopt.geonppang.domain.model

data class BreadType(
    val breadTypeId: Int,
    val breadTypeName: String,
    val isGlutenFree: Boolean,
    val isVegan: Boolean,
    val isNutFree: Boolean,
    val isSugarFree: Boolean,
)
