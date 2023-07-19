package com.sopt.geonppang.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.domain.model.BakeryInfo
import com.sopt.geonppang.domain.model.BookMark
import com.sopt.geonppang.domain.model.DetailReview
import com.sopt.geonppang.domain.model.ReviewData
import com.sopt.geonppang.domain.repository.DetailRepository
import com.sopt.geonppang.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailRepository: DetailRepository
) : ViewModel() {
    private val _reviewList = MutableStateFlow<List<DetailReview>?>(null)
    val reviewList get() = _reviewList.asStateFlow()

    private val _bakeryListState = MutableStateFlow<UiState<BakeryInfo>>(UiState.Loading)
    val bakeryListState get() = _bakeryListState.asStateFlow()

    private val _reviewListState = MutableStateFlow<UiState<ReviewData>>(UiState.Loading)
    val reviewListState get() = _reviewListState.asStateFlow()

    private val _bookMarkState = MutableStateFlow<BookMark?>(null)
    val bookMarkState get() = _bookMarkState.asStateFlow()

    fun fetchDetailBakeryInfo(bakeryId: Int) {
        viewModelScope.launch {
            detailRepository.fetchDetailBakery(bakeryId)
                .onSuccess { bakeryInfo ->
                    _bakeryListState.value = UiState.Success(bakeryInfo)
                    _bookMarkState.value = BookMark(bakeryInfo.bookMarkCount, bakeryInfo.isBooked)
                }
                .onFailure { throwable ->
                    _bakeryListState.value = UiState.Error(throwable.message)
                }
        }
    }

    fun fetchDetailReview(bakeryId: Int) {
        viewModelScope.launch {
            detailRepository.fetchDetailReview(bakeryId)
                .onSuccess { reviewData ->
                    _reviewListState.value = UiState.Success(reviewData)
                    _reviewList.value = reviewData.detailReviewList
                }
                .onFailure { throwable ->
                    _reviewListState.value = UiState.Error(throwable.message)
                }
        }
    }

    fun doBookMark(bakeryId: Int, isAddingBookMark: Boolean) {
        viewModelScope.launch {
            detailRepository.doBookMark(bakeryId, isAddingBookMark)
                .onSuccess { bookMark ->
                    _bookMarkState.value = bookMark
                }
                .onFailure { throwable ->
                    Timber.e(throwable.message)
                }
        }
    }
}
