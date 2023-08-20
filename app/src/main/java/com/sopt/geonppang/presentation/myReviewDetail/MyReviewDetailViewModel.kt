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
    private val _isLikeType = MutableStateFlow(true)
    val isLikeType get() = _isLikeType.asStateFlow()
    private val _myReviewText = MutableStateFlow("")
    val myReviewText get() = _myReviewText.asStateFlow()
    val isRecommendKeywordSelected = MutableStateFlow(
        mapOf(
            1 to false,
            2 to false,
            3 to false,
            4 to false
        )
    )
    private val _bakeryInfo = MutableStateFlow<MyReviewBakeryInfo?>(null)
    val bakeryInfo get() = _bakeryInfo.asStateFlow()


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

    private fun setSelectedRecommendKeyword(recommendKeyWordList: List<DetailReview.RecommendKeyword>) {
        val selectedKeyWord = isRecommendKeywordSelected.value.toMutableMap()
        for (recommendKeyword in recommendKeyWordList) {
            selectedKeyWord[recommendKeyword.recommendKeywordId] = true
        }
        isRecommendKeywordSelected.value = selectedKeyWord
    }

    fun setUserInfo(bakeryInfo: MyReviewBakeryInfo) {
        _bakeryInfo.value = bakeryInfo
    }
}
