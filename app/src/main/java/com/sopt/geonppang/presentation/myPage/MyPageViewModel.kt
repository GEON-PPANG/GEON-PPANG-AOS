package com.sopt.geonppang.presentation.myPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.domain.model.Bakery
import com.sopt.geonppang.domain.model.MyReview
import com.sopt.geonppang.domain.model.Profile
import com.sopt.geonppang.domain.repository.MyPageRepository
import com.sopt.geonppang.presentation.type.MainPurposeType
import com.sopt.geonppang.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository,
) : ViewModel() {
    private var _profileInfo = MutableStateFlow<Profile?>(null)
    val profileInfo get() = _profileInfo.asStateFlow()
    private var _isFilterSelected = MutableStateFlow(true)
    val isFilterSelected = _isFilterSelected.asStateFlow()
    private var _myPageReviewListState =
        MutableStateFlow<UiState<List<MyReview>>>(UiState.Loading)
    val myPageReviewListState get() = _myPageReviewListState.asStateFlow()
    private var _myReviewCount = MutableStateFlow<Int?>(null)
    val myReviewCount get() = _myReviewCount.asStateFlow()
    private var _myPageBookmarkListState =
        MutableStateFlow<UiState<List<Bakery>>>(UiState.Loading)
    val myPageBookmarkListState get() = _myPageBookmarkListState.asStateFlow()
    private var _myBookmarkCount = MutableStateFlow<Int?>(null)
    val myBookmarkCount get() = _myBookmarkCount.asStateFlow()

    fun fetchProfileInfo() {
        viewModelScope.launch {
            myPageRepository.fetchProfileInfo()
                .onSuccess { profile ->
                    _profileInfo.value = profile
                    _isFilterSelected.value = (profile.mainPurpose != NONE)
                }
        }
    }

    fun fetchMyPageReviewList() {
        viewModelScope.launch {
            myPageRepository.fetchMyReview().onSuccess { myReviewList ->
                _myPageReviewListState.value = UiState.Success(myReviewList)
                _myReviewCount.value = myReviewList.size
            }.onFailure { throwable ->
                _myPageReviewListState.value = UiState.Error(throwable.message)
            }
        }
    }

    fun fetchMyPageBookmarkList() {
        viewModelScope.launch {
            myPageRepository.fetchMyBookmark().onSuccess { myBookmarkList ->
                _myPageBookmarkListState.value = UiState.Success(myBookmarkList)
                _myBookmarkCount.value = myBookmarkList.size
            }.onFailure { throwable ->
                _myPageBookmarkListState.value = UiState.Error(throwable.message)
            }
        }
    }

    fun setMainPurposeTitle(): Int {
        return when (profileInfo.value?.mainPurpose) {
            MainPurposeType.DIET.name -> MainPurposeType.DIET.titleRes
            MainPurposeType.HEALTH.name -> MainPurposeType.HEALTH.titleRes
            else -> MainPurposeType.VEGAN.titleRes
        }
    }

    companion object {
        const val NONE = "NONE"
    }
}
