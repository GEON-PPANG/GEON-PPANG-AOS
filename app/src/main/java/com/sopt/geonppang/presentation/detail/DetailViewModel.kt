package com.sopt.geonppang.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.domain.model.BakeryInfo
import com.sopt.geonppang.domain.model.BookMark
import com.sopt.geonppang.domain.model.ReviewData
import com.sopt.geonppang.domain.repository.DetailRepository
import com.sopt.geonppang.presentation.model.BakeryReviewWritingInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailRepository: DetailRepository,
) : ViewModel() {
    private val _bakeryId = MutableStateFlow(-1)
    val bakeryId get() = _bakeryId.asStateFlow()
    private val _bakeryInfo = MutableStateFlow<BakeryInfo?>(null)
    val bakeryInfo get() = _bakeryInfo.asStateFlow()
    private val _reviewData = MutableStateFlow<ReviewData?>(null)
    val reviewData get() = _reviewData.asStateFlow()
    private val _bookMarkInfo = MutableStateFlow<BookMark?>(null)
    val bookMarkInfo get() = _bookMarkInfo.asStateFlow()

    fun fetchDetailBakeryInfo(bakeryId: Int) {
        viewModelScope.launch {
            _bakeryId.value = bakeryId
            detailRepository.fetchDetailBakery(bakeryId)
                .onSuccess { bakeryInfo ->
                    _bakeryInfo.value = bakeryInfo
                    _bookMarkInfo.value = BookMark(bakeryInfo.bookMarkCount, bakeryInfo.isBooked)
                }
                .onFailure { throwable ->
                    Timber.e(throwable.message)
                }
        }
    }

    fun fetchDetailReview(bakeryId: Int) {
        viewModelScope.launch {
            detailRepository.fetchDetailReview(bakeryId)
                .onSuccess { reviewData ->
                    _reviewData.value = reviewData
                }
                .onFailure { throwable ->
                    Timber.e(throwable.message)
                }
        }
    }

    fun doBookMark(bakeryId: Int, isAddingBookMark: Boolean) {
        viewModelScope.launch {
            detailRepository.doBookMark(bakeryId, isAddingBookMark)
                .onSuccess { bookMark ->
                    _bookMarkInfo.value = bookMark
                }
                .onFailure { throwable ->
                    Timber.e(throwable.message)
                }
        }
    }

    fun getBakeryInfo(): BakeryReviewWritingInfo {
        return _bakeryInfo.value?.let { _bakeryInfo ->
            BakeryReviewWritingInfo(
                _bakeryInfo.bakeryName,
                _bakeryInfo.bakeryPicture,
                BakeryReviewWritingInfo.BreadType(
                    _bakeryInfo.breadType.isGlutenFree,
                    _bakeryInfo.breadType.isVegan,
                    _bakeryInfo.breadType.isNutFree,
                    _bakeryInfo.breadType.isSugarFree,
                ),
                _bakeryInfo.firstNearStation,
                _bakeryInfo.secondNearStation
            )
        } ?: BakeryReviewWritingInfo("", "", null, "", "")
    }
}
