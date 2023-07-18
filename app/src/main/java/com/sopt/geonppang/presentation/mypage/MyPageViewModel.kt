package com.sopt.geonppang.presentation.mypage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.domain.model.Bakery
import com.sopt.geonppang.domain.model.BreadType
import com.sopt.geonppang.domain.model.MyReview
import com.sopt.geonppang.domain.model.Profile
import com.sopt.geonppang.domain.repository.MypageReviewRepository
import com.sopt.geonppang.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val mypageReviewRepository: MypageReviewRepository,
) : ViewModel() {
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

    private var _mypageReviewListState = MutableStateFlow<UiState<List<MyReview>>>(UiState.Loading)
    val mypageReviewListState get() = _mypageReviewListState.asStateFlow()

    private var _myReviewCount = MutableStateFlow<Int?>(null)
    val myReviewCount get() = _myReviewCount.asStateFlow()

    init {
        fetchMypageReviewList()
    }

    private fun fetchMypageReviewList() {
        viewModelScope.launch {
            mypageReviewRepository.fetchMyReview()
                .onSuccess { myReviewList ->
                    _mypageReviewListState.value = UiState.Success(myReviewList)
                    _myReviewCount.value = myReviewList.size
                }
                .onFailure { throwable ->
                    _mypageReviewListState.value = UiState.Error(throwable.message)
                }
        }
    }

    // 마이페이지 북마크 더미데이터
    val mockStoreList = listOf(
        Bakery(
            bakeryId = 1,
            bakeryName = "건대 비건빵아아아아아아아아아",
            firstNearStation = "건대역",
            secondNearStation = null,
            bookmarkCount = 5,
            bakeryPicture = "https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20220526_91%2F1653554529250qdOYp_JPEG%2F0E35EAC3-F936-41C7-BEE4-645B83AED8B1.jpeg",
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
            bookmarkCount = 7,
            bakeryPicture = "https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20220526_91%2F1653554529250qdOYp_JPEG%2F0E35EAC3-F936-41C7-BEE4-645B83AED8B1.jpeg",
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
            bookmarkCount = 0,
            bakeryPicture = "https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20220526_91%2F1653554529250qdOYp_JPEG%2F0E35EAC3-F936-41C7-BEE4-645B83AED8B1.jpeg",
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
}
