package com.sopt.geonppang.data.model.response

import com.sopt.geonppang.domain.model.BakeryInfo
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDetailBakery(
    val code: Int,
    val data: Data,
    val message: String
) {
    @Serializable
    data class Data(
        val address: String,
        val bakeryId: Int,
        val bakeryName: String,
        val bakeryPicture: String,
        val bookMarkCount: Int,
        val breadType: BreadType,
        val closedDay: String,
        val firstNearStation: String,
        val homepage: String,
        val isBookMarked: Boolean,
        val isHACCP: Boolean,
        val isNonGMO: Boolean,
        val isVegan: Boolean,
        val menuList: List<Menu>,
        val openingTime: String,
        val phoneNumber: String,
        val reviewCount: Int,
        val secondNearStation: String
    ) {
        @Serializable
        data class BreadType(
            val breadTypeId: Int,
            val breadTypeName: String,
            val isGlutenFree: Boolean,
            val isNutFree: Boolean,
            val isSugarFree: Boolean,
            val isVegan: Boolean
        )

        @Serializable
        data class Menu(
            val menuId: Int,
            val menuName: String,
            val menuPrice: Int
        )

        fun toBakeryInfo() = BakeryInfo(
            bakeryId = bakeryId,
            bakeryName = bakeryName,
            bakeryPicture = bakeryPicture,
            isHACCP = isHACCP,
            isVegan = isVegan,
            isNonGMO = isNonGMO,
            breadType = toBreadType(),
            firstNearStation = firstNearStation,
            secondNearStation = secondNearStation,
            isBooked = isBookMarked,
            bookMarkCount = bookMarkCount,
            reviewCount = reviewCount,
            homepage = homepage,
            address = address,
            openingTime = openingTime,
            closedDay = closedDay,
            phoneNumber = phoneNumber
        )

        fun toBreadType() = com.sopt.geonppang.domain.model.BreadType(
            breadTypeId = breadType.breadTypeId,
            breadTypeName = breadType.breadTypeName,
            isGlutenFree = breadType.isGlutenFree,
            isVegan = breadType.isVegan,
            isNutFree = breadType.isNutFree,
            isSugarFree = breadType.isSugarFree
        )
    }l
}
