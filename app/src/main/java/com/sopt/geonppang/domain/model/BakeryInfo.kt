package com.sopt.geonppang.domain.model

import com.sopt.geonppang.presentation.type.BreadFilterType

data class BakeryInfo(
    val bakeryId: Int,
    val bakeryName: String,
    val bakeryPicture: String,
    val isHACCP: Boolean,
    val isVegan: Boolean,
    val isNonGMO: Boolean,
    val breadTypeIdList: List<BreadFilterType>,
    val firstNearStation: String,
    val secondNearStation: String,
    val isBooked: Boolean,
    val bookMarkCount: Int,
    val reviewCount: Int,
    val mapUrl: String,
    val homepageUrl: String,
    val instagramUrl: String,
    val address: String,
    val openingHours: String,
    val closedDay: String,
    val phoneNumber: String,
    val menuList: List<Menu>
)
