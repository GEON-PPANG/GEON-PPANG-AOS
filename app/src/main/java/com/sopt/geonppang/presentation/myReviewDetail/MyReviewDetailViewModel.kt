package com.sopt.geonppang.presentation.myReviewDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.domain.model.DetailReview
import com.sopt.geonppang.domain.repository.MyReviewDetailRepository
import com.sopt.geonppang.presentation.model.MyReviewBakeryInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyReviewDetailViewModel @Inject constructor(
    private val myReviewDetailRepository: MyReviewDetailRepository
) : ViewModel() {
    private val _bakeryInfo = MutableStateFlow<MyReviewBakeryInfo?>(null)
    val bakeryInfo get() = _bakeryInfo.asStateFlow()
    private val _isLikeType = MutableStateFlow(true)
    val isLikeType get() = _isLikeType.asStateFlow()
    val isRecommendKeywordSelected = MutableStateFlow(
        mapOf(
            TASTY to false,
            KIND to false,
            SPECIAL_MENU to false,
            ZERO_WASTE to false
        )
    )
    private val _myReviewText = MutableStateFlow("")
    val myReviewText get() = _myReviewText.asStateFlow()

    private fun setSelectedRecommendKeyword(recommendKeyWordList: List<DetailReview.RecommendKeyword>) {
        val selectedKeyWord = isRecommendKeywordSelected.value.toMutableMap()
        for (recommendKeyword in recommendKeyWordList) {
            selectedKeyWord[recommendKeyword.recommendKeywordId] = true
        }
        isRecommendKeywordSelected.value = selectedKeyWord
    }

    fun setBakeryInfo(bakeryInfo: MyReviewBakeryInfo) {
        _bakeryInfo.value = bakeryInfo
    }

    fun fetchMyReviewDetail(reviewId: Int) {
        viewModelScope.launch {
            myReviewDetailRepository.fetchMyReviewDetail(reviewId)
                .onSuccess { myReviewDetail ->
                    _isLikeType.value = myReviewDetail.isLike
                    _myReviewText.value = myReviewDetail.reviewText
                    setSelectedRecommendKeyword(myReviewDetail.recommendKeywordList)
                }
                .onFailure { throwable ->
                    Timber.e(throwable.message)
                }
        }
    }

    companion object {
        const val TASTY = 1
        const val KIND = 2
        const val SPECIAL_MENU = 3
        const val ZERO_WASTE = 4
    }
}
