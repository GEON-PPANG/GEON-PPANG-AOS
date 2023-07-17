package com.sopt.geonppang.presentation.detail

import androidx.lifecycle.ViewModel
import com.sopt.geonppang.R
import com.sopt.geonppang.domain.model.BakeryInfo
import com.sopt.geonppang.domain.model.BreadType
import com.sopt.geonppang.domain.model.DetailReview
import com.sopt.geonppang.domain.model.Menu
import com.sopt.geonppang.domain.model.ReviewData

class DetailViewModel : ViewModel() {
    val mockBakeryInfo = BakeryInfo(
        bakeryName = "모니모니해도비건",
        bakeryPicture = R.drawable.bakery,
        isHACCP = true,
        isVegan = false,
        isNonGMO = true,
        breadType = BreadType(
            breadTypeName = "글루텐프리",
            breadTypeId = 1,
            isGlutenFree = true,
            isVegan = true,
            isNutFree = false,
            isSugarFree = false
        ),
        firstNearStation = "의왕역",
        secondNearStation = null,
        isBooked = true,
        bookmarkCount = 7,
        reviewCount = 10,
        homepage = "https://www.naver.com/mmv_vegan_bake_shop/",
        address = "경기 의왕시 신장승길 29 퍼스트힐5차 111호",
        openingTime = "수-금 12:00 ~ 19:00 / 토-일 13:00 ~ 19:00",
        closedTime = "월,화 휴무",
        phoneNumber = "02-033-3333"
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

    val mockDetailReviewData = ReviewData(
        taste = 20,
        special = 50,
        kind = 10,
        zero = 90,
        reviewCount = 3,
        detailReviewList = listOf(
            DetailReview(
                reviewText = "맛있어서 좋았어요",
                memberNickname = "건빵죽빵빵빵빵",
                createdAt = "23.07.13",
                recommendKeywordList = listOf(
                    DetailReview.RecommendKeyword(
                        recommendKeywordId = 1,
                        recommendKeywordName = "맛있어요"
                    ),
                    DetailReview.RecommendKeyword(
                        recommendKeywordId = 4,
                        recommendKeywordName = "제로웨이스트"
                    )
                )
            ),
            DetailReview(
                reviewText = "여기 소금빵 미친 존맛탱임 우리 건빵 가족들에게도 알려주고싶은 맛이에용앙아아아아아아아아아아아ㅏㅇ아ㅏ아아아아아아아아아ㅏ아아",
                memberNickname = "보연티비",
                createdAt = "23.08.09",
                recommendKeywordList = listOf(
                    DetailReview.RecommendKeyword(
                        recommendKeywordId = 1,
                        recommendKeywordName = "맛있어요"
                    ),
                    DetailReview.RecommendKeyword(
                        recommendKeywordId = 3,
                        recommendKeywordName = "친절해요"
                    ),
                    DetailReview.RecommendKeyword(
                        recommendKeywordId = 4,
                        recommendKeywordName = "제로웨이스트"
                    )
                )
            ),
            DetailReview(
                reviewText = "안빵이들아 사랑행",
                memberNickname = "안빵빵",
                createdAt = "23.07.13",
                recommendKeywordList = listOf(
                    DetailReview.RecommendKeyword(
                        recommendKeywordId = 2,
                        recommendKeywordName = "특별한 메뉴"
                    ),
                    DetailReview.RecommendKeyword(
                        recommendKeywordId = 3,
                        recommendKeywordName = "친절해요"
                    ),
                    DetailReview.RecommendKeyword(
                        recommendKeywordId = 4,
                        recommendKeywordName = "제로웨이스트"
                    )
                )
            )
        )
    )
}
