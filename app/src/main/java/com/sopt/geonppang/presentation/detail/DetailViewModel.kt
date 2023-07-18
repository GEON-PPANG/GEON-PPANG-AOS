package com.sopt.geonppang.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.domain.model.BakeryInfo
import com.sopt.geonppang.domain.model.DetailReview
import com.sopt.geonppang.domain.model.Menu
import com.sopt.geonppang.domain.model.ReviewData
import com.sopt.geonppang.domain.repository.DetailRepository
import com.sopt.geonppang.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailRepository: DetailRepository
) : ViewModel() {
    private val _bakeryListState = MutableStateFlow<UiState<BakeryInfo>>(UiState.Loading)
    val bakeryListState get() = _bakeryListState.asStateFlow()

    init {
        fetchDetailBakeryInfo(1)
    }

    private fun fetchDetailBakeryInfo(bakeryId: Int) {
        viewModelScope.launch {
            detailRepository.fetchDetailBakery(bakeryId)
                .onSuccess { bakeryInfo ->
                    _bakeryListState.value = UiState.Success(bakeryInfo)
                }
                .onFailure { throwable ->
                    _bakeryListState.value = UiState.Error(throwable.message)
                }
        }
    }

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
