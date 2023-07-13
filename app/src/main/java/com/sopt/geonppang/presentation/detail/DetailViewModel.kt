package com.sopt.geonppang.presentation.detail

import androidx.lifecycle.ViewModel
import com.sopt.geonppang.R
import com.sopt.geonppang.domain.model.BakeryInfo
import com.sopt.geonppang.domain.model.BreadType

class DetailViewModel : ViewModel() {
    val mockBakeryInfo = listOf(
        BakeryInfo(
            bakeryName = "모니모니해도비건",
            bakeryPicture = R.drawable.bakery,
            isHACCP = true,
            isVegan = false,
            isNonGMO = true,
            breadType = BreadType(
                isGlutenFree = true,
                isVegan = true,
                isNutFree = false,
                isSugarFree = false
            ),
            firstNearStation = "의왕역",
            secondNearStation = null,
            isBooked = true,
            bookmarkCount = 7,
            homepage = "https://www.naver.com/mmv_vegan_bake _shop/",
            address = "경기 의왕시 신장승길 29 퍼스트힐5차 111호",
            openingTime = "수-금 12:00 ~ 19:00 / 토-일 13:00 ~ 19:00",
            closedTime = "월,화 휴무",
            phoneNumber = "02-033-3333"
        )
    )
}
