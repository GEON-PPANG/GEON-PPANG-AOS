package com.sopt.geonppang.domain.model

data class Bakery(
    val bakeryId: Int,
    val bakeryName: String,
    val isHACCP: Boolean,
    val isVegan: Boolean,
    val isNonGMO: Boolean,
    val firstNearStation: String,
    val secondNearStation: String?,
    val bookmarkCount: Int,
    val bakeryPicture: String,
    val breadType: BreadType,
)
