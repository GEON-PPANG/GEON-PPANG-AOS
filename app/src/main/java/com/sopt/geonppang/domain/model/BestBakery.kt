package com.sopt.geonppang.domain.model

data class BestBakery(
    val bakeryId: Int,
    val bakeryName: String,
    val firstNearStation: String,
    val secondNearStation: String,
    val bookmarkCount: Int,
    val bakeryImage: String,
    val reviewCount: Int,
    val isHACCP: Boolean,
    val isVegan: Boolean,
    val isNonGMO: Boolean,
)
