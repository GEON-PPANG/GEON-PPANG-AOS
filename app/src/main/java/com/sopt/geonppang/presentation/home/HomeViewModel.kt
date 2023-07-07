package com.sopt.geonppang.presentation.home

import androidx.lifecycle.ViewModel
import com.sopt.geonppang.R
import com.sopt.geonppang.domain.model.BestBakery
import com.sopt.geonppang.domain.model.BestReview

class HomeViewModel : ViewModel() {
    val mockBestBakeryList = listOf(
        BestBakery(
            bakeryId = 1,
            bakeryName = "건대 비건빵아아아아아아",
            firstNearStation = "건대역",
            secondNearStation = null,
            isBooked = true,
            bookmarkCount = 5,
            bakeryImage = R.drawable.bread1,
            reviewCount = 5,
        ),
        BestBakery(
            bakeryId = 2,
            bakeryName = "건대 비건빵아아아아아아",
            firstNearStation = "덕소역",
            secondNearStation = "구리역",
            isBooked = false,
            bookmarkCount = 0,
            bakeryImage = R.drawable.bread1,
            reviewCount = 5,
        ),
        BestBakery(
            bakeryId = 3,
            bakeryName = "건대 비건빵아아아아아아",
            firstNearStation = "양정역",
            secondNearStation = "하암역",
            isBooked = false,
            bookmarkCount = 5,
            bakeryImage = R.drawable.bread1,
            reviewCount = 5,
        ),
        BestBakery(
            bakeryId = 4,
            bakeryName = "건대 비건빵아아아아아아",
            firstNearStation = "양원역",
            secondNearStation = "후앙역",
            isBooked = true,
            bookmarkCount = 5,
            bakeryImage = R.drawable.bread1,
            reviewCount = 5,
        ),
    )

    val mockBestReviewList = listOf(
        BestReview(
            bakeryId = 1,
            bakeryName = "건대 비건빵아아아아아아",
            isBooked = true,
            bookmarkCount = 5,
            bakeryImage = R.drawable.bread1,
            reviewCount = 4,
            reviewText = "정말 너무 맛있었어요! 갓 구운빵이 예술이었던 것 같아요오오오오오",
            firstReviewChip = "친절해요",
            secondReviewChip = "제로웨이스트"
        ),
        BestReview(
            bakeryId = 2,
            bakeryName = "건대 비건빵아아아아아아",
            isBooked = true,
            bookmarkCount = 5,
            bakeryImage = R.drawable.bread1,
            reviewCount = 4,
            reviewText = "정말 너무 맛있었어요! 갓 구운빵이 예술이었던 것 같아요오오오오오",
            firstReviewChip = "친절해요",
            secondReviewChip = "제로웨이스트"
        ),
        BestReview(
            bakeryId = 3,
            bakeryName = "건대 비건빵아아아아아아",
            isBooked = true,
            bookmarkCount = 5,
            bakeryImage = R.drawable.bread1,
            reviewCount = 4,
            reviewText = "정말 너무 맛있었어요! 갓 구운빵이 예술이었던 것 같아요오오오오오",
            firstReviewChip = "친절해요",
            secondReviewChip = "제로웨이스트"
        ),
        BestReview(
            bakeryId = 4,
            bakeryName = "건대 비건빵아아아아아아",
            isBooked = true,
            bookmarkCount = 5,
            bakeryImage = R.drawable.bread1,
            reviewCount = 4,
            reviewText = "정말 너무 맛있었어요! 갓 구운빵이 예술이었던 것 같아요오오오오오",
            firstReviewChip = "친절해요",
            secondReviewChip = "제로웨이스트"
        ),
    )
}
