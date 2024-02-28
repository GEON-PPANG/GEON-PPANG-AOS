package com.sopt.geonppang.domain.model

import com.sopt.geonppang.presentation.type.BakerySortType

data class BakeryListFilterType(
    val sortType: BakerySortType = BakerySortType.DEFAULT,
    val isPersonalFilterApplied: Boolean,
    val isHard: Boolean = false,
    val isDessert: Boolean = false,
    val isBrunch: Boolean = false
)
