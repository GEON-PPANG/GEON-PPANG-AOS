package com.sopt.geonppang.presentation.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.domain.model.Bakery
import com.sopt.geonppang.domain.model.MyReview
import com.sopt.geonppang.domain.model.Profile
import com.sopt.geonppang.domain.repository.MypageRepository
import com.sopt.geonppang.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val mypageRepository: MypageRepository,
) : ViewModel() {
    private var _mypageInfoState = MutableStateFlow<Profile?>(null)
    val mypageInfoState get() = _mypageInfoState.asStateFlow()

    private var _mypageReviewListState =
        MutableStateFlow<UiState<List<MyReview>>>(UiState.Loading)
    val mypageReviewListState get() = _mypageReviewListState.asStateFlow()

    private var _myReviewCount = MutableStateFlow<Int?>(null)
    val myReviewCount get() = _myReviewCount.asStateFlow()

    private var _mypageBookmarkListState =
        MutableStateFlow<UiState<List<Bakery>>>(UiState.Loading)
    val mypageBookmarkListState get() = _mypageBookmarkListState.asStateFlow()

    private var _myBookmarkCount = MutableStateFlow<Int?>(null)
    val myBookmarkCount get() = _myBookmarkCount.asStateFlow()

    init {
        fetchMypageReviewList()
        fetchMypageBookmarkList()
    }

    fun fetchMypageInfo() {
        viewModelScope.launch {
            mypageRepository.fetchMypageInfo()
                .onSuccess { myInfo ->
                    myInfo.let {
                        _mypageInfoState.value = myInfo
                    }
                }
        }
    }

    private fun fetchMypageReviewList() {
        viewModelScope.launch {
            mypageRepository.fetchMyReview().onSuccess { myReviewList ->
                _mypageReviewListState.value = UiState.Success(myReviewList)
                _myReviewCount.value = myReviewList.size
            }.onFailure { throwable ->
                _mypageReviewListState.value = UiState.Error(throwable.message)
            }
        }
    }

    private fun fetchMypageBookmarkList() {
        viewModelScope.launch {
            mypageRepository.fetchMyBookmark().onSuccess { myBookmarkList ->
                _mypageBookmarkListState.value = UiState.Success(myBookmarkList)
                _myBookmarkCount.value = myBookmarkList.size
                Timber.e(_myBookmarkCount.value.toString())
            }.onFailure { throwable ->
                _mypageBookmarkListState.value = UiState.Error(throwable.message)
            }
        }
    }
}
