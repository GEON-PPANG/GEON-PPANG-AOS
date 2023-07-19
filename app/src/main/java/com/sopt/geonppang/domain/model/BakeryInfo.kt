package com.sopt.geonppang.domain.model

data class BakeryInfo(
    val bakeryId: Int,
    val bakeryName: String,
    val bakeryPicture: String,
    val isHACCP: Boolean,
    val isVegan: Boolean,
    val isNonGMO: Boolean,
    val breadType: BreadType,
    val firstNearStation: String,
    val secondNearStation: String?,
    val isBooked: Boolean,
    val bookMarkCount: Int,
    val reviewCount: Int,
    val homepage: String?,
    val address: String,
    val openingTime: String,
    val closedDay: String,
    val phoneNumber: String,
    val menuList: List<Menu>
)
