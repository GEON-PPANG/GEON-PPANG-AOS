package com.sopt.geonppang.domain.model

import com.sopt.geonppang.presentation.type.BreadFilterType

// TODO: breadType 형태 완전 변환 후 차후 삭제
data class Bakery(
    val bakeryId: Int,
    val bakeryName: String,
    val isHACCP: Boolean,
    val isVegan: Boolean,
    val isNonGMO: Boolean,
    val firstNearStation: String,
    val secondNearStation: String,
    val bookmarkCount: Int?,
    val bakeryPicture: String,
    val breadType: BreadType,
)

// TODO: 차후 네이밍 수정
data class BakeryInformation(
    val bakeryId: Int,
    val bakeryName: String,
    val isHACCP: Boolean,
    val isVegan: Boolean,
    val isNonGMO: Boolean,
    val firstNearStation: String,
    val secondNearStation: String,
    val bookmarkCount: Int?,
    val bakeryPicture: String,
    val breadTypeList: List<BreadFilterType>,
)