package com.sopt.geonppang.presentation.detail

import androidx.lifecycle.ViewModel
import com.sopt.geonppang.R
import com.sopt.geonppang.domain.model.BakeryInfo
import com.sopt.geonppang.domain.model.BreadType
import com.sopt.geonppang.domain.model.Menu
import com.sopt.geonppang.domain.model.Review
import com.sopt.geonppang.domain.model.ReviewData

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
            homepage = "https://www.naver.com/mmv_vegan_bake_shop/",
            address = "경기 의왕시 신장승길 29 퍼스트힐5차 111호",
            openingTime = "수-금 12:00 ~ 19:00 / 토-일 13:00 ~ 19:00",
            closedTime = "월,화 휴무",
            phoneNumber = "02-033-3333"
        )
    )

    val mockMenuList = listOf(
        Menu(
            menuName = "쌀 소금빵",
            menuPrice = 3500
        ),
        Menu(
            menuName = "100%통밀 비건 스페셜 캄파뉴",
            menuPrice = 130500
        ),
        Menu(
            menuName = "요거요거요거바라 블루베리 케이크",
            menuPrice = 31500
        )
    )

    val mockReviewData = listOf(
        ReviewData(
            taste = 20,
            special = 50,
            kind = 10,
            zero = 90,
            reviewCount = 4
        )
    )

    val mockReview = listOf(
        Review(
            reviewText = "맛있어서 좋았어요",
            memberNickname = "건빵죽빵빵빵빵",
            createdAt = "23.07.13"
        ),
        Review(
            reviewText = "여기 소금빵 미친 존맛탱임 우리 건빵 가족들에게도 알려주고싶은 맛이에용앙아아아아아아아아아아아ㅏㅇ아ㅏ아아아아아아아아아ㅏ아아",
            memberNickname = "보연티비",
            createdAt = "23.08.09"
        ),
        Review(
            reviewText = "안빵이들아 사랑행",
            memberNickname = "안빵빵",
            createdAt = "23.07.13"
        )
    )
}
