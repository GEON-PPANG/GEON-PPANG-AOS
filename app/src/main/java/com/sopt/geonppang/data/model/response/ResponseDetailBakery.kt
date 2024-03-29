package com.sopt.geonppang.data.model.response

import com.sopt.geonppang.domain.model.BakeryInfo
import com.sopt.geonppang.domain.model.Menu
import com.sopt.geonppang.presentation.type.BreadFilterType
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
        val breadTypeList: List<BreadTypeIdDto>,
        val closedDay: String,
        val firstNearStation: String,
        val mapUrl: String,
        val homepageUrl: String,
        val instagramUrl: String,
        val isBookMarked: Boolean,
        val isHACCP: Boolean,
        val isNonGMO: Boolean,
        val isVegan: Boolean,
        val menuList: List<Menu>,
        val openingHours: String,
        val phoneNumber: String,
        val reviewCount: Int,
        val secondNearStation: String,
    ) {
        @Serializable
        data class Menu(
            val menuId: Int,
            val menuName: String,
            val menuPrice: Int
        )
    }

    fun toBakeryInfo() = BakeryInfo(
        bakeryId = data.bakeryId,
        bakeryName = data.bakeryName,
        bakeryPicture = data.bakeryPicture,
        isHACCP = data.isHACCP,
        isVegan = data.isVegan,
        isNonGMO = data.isNonGMO,
        firstNearStation = data.firstNearStation,
        secondNearStation = data.secondNearStation,
        isBooked = data.isBookMarked,
        bookMarkCount = data.bookMarkCount,
        reviewCount = data.reviewCount,
        mapUrl = data.mapUrl,
        homepageUrl = data.homepageUrl,
        instagramUrl = data.instagramUrl,
        address = data.address,
        openingHours = data.openingHours,
        closedDay = data.closedDay,
        phoneNumber = data.phoneNumber,
        menuList = data.menuList.map { menu ->
            Menu(
                menuId = menu.menuId,
                menuName = menu.menuName,
                menuPrice = menu.menuPrice
            )
        },
        breadTypeIdList = data.breadTypeList.mapNotNull { breadTypeIdDto ->
            BreadFilterType.values().find {
                it.id == breadTypeIdDto.breadTypeId
            }
        }
    )
}
