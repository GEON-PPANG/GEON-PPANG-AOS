package com.sopt.geonppang.presentation.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.domain.model.Bakery
import com.sopt.geonppang.domain.model.BreadType
import com.sopt.geonppang.domain.model.MyReview
import com.sopt.geonppang.domain.model.Profile
import com.sopt.geonppang.domain.repository.MypageBookmarkRepository
import com.sopt.geonppang.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val mypageBookmarkRepository: MypageBookmarkRepository,
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

    private var _mypageBookmarkListState = MutableStateFlow<UiState<List<Bakery>>>(UiState.Loading)
    val mypageBookmarkListState get() = _mypageBookmarkListState.asStateFlow()

    private var _myBookmarkCount = MutableStateFlow<Int?>(null)
    val myBookmarkCount get() = _myBookmarkCount.asStateFlow()

    init {
        fetchMypageBookmarkList()
    }

    private fun fetchMypageBookmarkList() {
        viewModelScope.launch {
            mypageBookmarkRepository.fetchMyBookmark()
                .onSuccess { myBookmarkList ->
                    _mypageBookmarkListState.value = UiState.Success(myBookmarkList)
                    _myBookmarkCount.value = myBookmarkList.size
                    Timber.e(_myBookmarkCount.value.toString())
                }
                .onFailure { throwable ->
                    _mypageBookmarkListState.value = UiState.Error(throwable.message)
                }
        }
    }


    val mockMyReviewList = listOf(
        MyReview(
            reviewId = 1,
            date = "23.07.14",
            bakery = Bakery(
                bakeryId = 1,
                bakeryName = "건대 비건빵아아아아아아아아아",
                firstNearStation = "건대역",
                secondNearStation = "성수역",
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
            )
        ),
        MyReview(
            reviewId = 2,
            date = "23.07.15",
            bakery = Bakery(
                bakeryId = 1,
                bakeryName = "건대 비건빵아아아아아아아아아",
                firstNearStation = "동대문역사공원역",
                secondNearStation = null,
                bookmarkCount = 6,
                bakeryPicture = "https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20220526_91%2F1653554529250qdOYp_JPEG%2F0E35EAC3-F936-41C7-BEE4-645B83AED8B1.jpeg",
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
        MyReview(
            reviewId = 3,
            date = "23.07.16",
            bakery = Bakery(
                bakeryId = 1,
                bakeryName = "건대 비건빵아아아아아아아아아",
                firstNearStation = "건대역",
                secondNearStation = null,
                bookmarkCount = 0,
                bakeryPicture = "https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20220526_91%2F1653554529250qdOYp_JPEG%2F0E35EAC3-F936-41C7-BEE4-645B83AED8B1.jpeg",
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
