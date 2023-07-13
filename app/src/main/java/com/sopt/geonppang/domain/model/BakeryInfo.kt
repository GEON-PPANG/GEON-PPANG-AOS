package com.sopt.geonppang.domain.model

data class BakeryInfo(
    val bakeryName: String,
    val bakeryPicture: Int,
    val isHACCP: Boolean,
    val isVegan: Boolean,
    val isNonGMO: Boolean,
    val breadType: BreadType,
    val firstNearStation: String,
    val secondNearStation: String?,
    val isBooked: Boolean,
    val bookmarkCount: Int,
    val homepage: String,
    val address: String,
    val openingTime: String,
    val closedTime: String,
    val phoneNumber: String
)
