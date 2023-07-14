package com.sopt.geonppang.presentation.mypage

import androidx.lifecycle.ViewModel
import com.sopt.geonppang.R
import com.sopt.geonppang.domain.model.Bakery
import com.sopt.geonppang.domain.model.BreadType
import com.sopt.geonppang.domain.model.Profile
import com.sopt.geonppang.domain.model.Review

class MyPageViewModel : ViewModel() {
    private val _profile = Profile(
        memberNickname = "안빵이들",
        mainPurpose = "맛 . 다이어트",
        breadType = BreadType(
            breadTypeId = 1,
            breadTypeName = "글루텐프리",
            isGlutenFree = true,
            isVegan = true,
            isNutFree = false,
            isSugarFree = true
        )
    )
    val profile = _profile

    // 마이페이지 북마크 더미데이터
    val mockStoreList = listOf(
        Bakery(
            bakeryId = 1,
            bakeryName = "건대 비건빵아아아아아아아아아",
            firstNearStation = "건대역",
            secondNearStation = null,
            isBooked = true,
            bookmarkCount = 5,
            bakeryPicture = R.drawable.bbang1,
            isHACCP = true,
            isNonGMO = true,
            isVegan = true,
            breadType = BreadType(
                breadTypeId = 1,
                breadTypeName = "글루텐프리",
                isGlutenFree = true,
                isVegan = true,
                isNutFree = true,
                isSugarFree = true,
            )
        ),
        Bakery(
            bakeryId = 2,
            bakeryName = "건대 비건빵아아아아아아",
            firstNearStation = "덕소역",
            secondNearStation = "구리역",
            isBooked = false,
            bookmarkCount = 7,
            bakeryPicture = R.drawable.bbang1,
            isHACCP = false,
            isNonGMO = true,
            isVegan = true,
            breadType = BreadType(
                breadTypeId = 2,
                breadTypeName = "글루텐프리",
                isGlutenFree = false,
                isVegan = true,
                isNutFree = true,
                isSugarFree = true,
            )
        ),
        Bakery(
            bakeryId = 3,
            bakeryName = "건대 비건빵아아아아아아",
            firstNearStation = "덕소역",
            secondNearStation = "구리역",
            isBooked = false,
            bookmarkCount = 0,
            bakeryPicture = R.drawable.bbang1,
            isHACCP = false,
            isNonGMO = true,
            isVegan = false,
            breadType = BreadType(
                breadTypeId = 3,
                breadTypeName = "글루텐프리",
                isGlutenFree = true,
                isVegan = true,
                isNutFree = true,
                isSugarFree = false,
            )
        ),
    )

    val mockMyReviewList = listOf(
        Review(
            reviewId = 1,
            date = "23.07.14",
            bakery = Bakery(
                bakeryId = 1,
                bakeryName = "건대 비건빵아아아아아아아아아",
                firstNearStation = "건대역",
                secondNearStation = "성수역",
                isBooked = true,
                bookmarkCount = 5,
                bakeryPicture = R.drawable.bbang1,
                isHACCP = true,
                isNonGMO = true,
                isVegan = true,
                breadType = BreadType(
                    breadTypeId = 1,
                    breadTypeName = "글루텐프리",
                    isGlutenFree = true,
                    isVegan = true,
                    isNutFree = true,
                    isSugarFree = true,
                )
            )
        ),
        Review(
            reviewId = 2,
            date = "23.07.15",
            bakery = Bakery(
                bakeryId = 1,
                bakeryName = "건대 비건빵아아아아아아아아아",
                firstNearStation = "동대문역사공원역",
                secondNearStation = null,
                isBooked = false,
                bookmarkCount = 6,
                bakeryPicture = R.drawable.bbang1,
                isHACCP = false,
                isNonGMO = true,
                isVegan = true,
                breadType = BreadType(
                    breadTypeId = 1,
                    breadTypeName = "글루텐프리",
                    isGlutenFree = true,
                    isVegan = false,
                    isNutFree = false,
                    isSugarFree = true,
                )
            )
        ),
        Review(
            reviewId = 3,
            date = "23.07.16",
            bakery = Bakery(
                bakeryId = 1,
                bakeryName = "건대 비건빵아아아아아아아아아",
                firstNearStation = "건대역",
                secondNearStation = null,
                isBooked = false,
                bookmarkCount = 0,
                bakeryPicture = R.drawable.bbang1,
                isHACCP = false,
                isNonGMO = true,
                isVegan = true,
                breadType = BreadType(
                    breadTypeId = 1,
                    breadTypeName = "글루텐프리",
                    isGlutenFree = true,
                    isVegan = false,
                    isNutFree = true,
                    isSugarFree = true,
                )
            )
        ),
    )
}
