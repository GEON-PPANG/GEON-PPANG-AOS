package com.sopt.geonppang.domain.model

data class BreadType(
    var isGlutenFree: Boolean,
    val isVegan: Boolean,
    val isNutFree: Boolean,
    val isSugarFree: Boolean,
)
