package com.sopt.geonppang.presentation.reviewWriting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.data.model.request.RequestReviewWriting
import com.sopt.geonppang.domain.repository.ReviewWritingRepository
import com.sopt.geonppang.presentation.model.BakeryReviewWritingInfo
import com.sopt.geonppang.presentation.type.KeyWordType
import com.sopt.geonppang.presentation.type.LikeType
import com.sopt.geonppang.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.set

@HiltViewModel
class ReviewWritingViewModel @Inject constructor(private val reviewWritingRepository: ReviewWritingRepository) :
    ViewModel() {

    val reviewText = MutableStateFlow("")
    private val _bakeryId = MutableStateFlow(-1)
    val bakeryId get() = _bakeryId.asStateFlow()
    private val _isLike = MutableStateFlow<LikeType?>(null)
    val isLike get() = _isLike.asStateFlow()
    private val _reviewSuccessState = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val reviewSuccessState get() = _reviewSuccessState
    private val _reviewCancelState = MutableStateFlow<Boolean?>(null)
    val reviewCancelState get() = _reviewCancelState
    private val _bakeryInfo = MutableStateFlow<BakeryReviewWritingInfo?>(null)
    val bakeryInfo get() = _bakeryInfo
    val userKeyWordType: MutableStateFlow<Map<KeyWordType, Boolean>> =
        MutableStateFlow(
            mapOf(
                KeyWordType.DELICIOUS to false,
                KeyWordType.KIND to false,
                KeyWordType.SPECIAL_MENU to false,
                KeyWordType.ZERO_WASTE to false
            )
        )
    val isUserKeyWordTypeSelected: StateFlow<Boolean> = userKeyWordType.map { keywordMap ->
        keywordMap.values.any { it }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)
    val isReviewWritingEnabled: StateFlow<Boolean> = combine(
        isLike,
        isUserKeyWordTypeSelected,
        reviewText
    ) { isLike, isUserKeyWordTypeSelected, reviewText ->
        val isLikeEnabled = isLike == LikeType.LIKE && isUserKeyWordTypeSelected
        val isBadEnabled = isLike == LikeType.BAD && reviewText.isNotBlank()
        isLikeEnabled || isBadEnabled
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    fun setBakeryId(bakeryId: Int) {
        _bakeryId.value = bakeryId
    }

    fun setLikeType(isLike: LikeType) {
        _isLike.value = isLike
    }

    fun setReviewCancelState(isCancel: Boolean) {
        _reviewCancelState.value = isCancel
    }

    fun setKeyWordType(keyWordType: KeyWordType) {
        val isSelected = userKeyWordType.value[keyWordType] ?: return
        userKeyWordType.value = userKeyWordType.value.toMutableMap().apply {
            this[keyWordType] = !isSelected
        }
    }

    private fun getKeyWordTypeList(): List<RequestReviewWriting.KeywordName> {
        val trueKeyWordTypes = mutableListOf<RequestReviewWriting.KeywordName>()
        userKeyWordType.value?.forEach { (keyWordType, value) ->
            if (value) {
                trueKeyWordTypes.add(RequestReviewWriting.KeywordName(keyWordType.keywordType))
            }
        }
        return trueKeyWordTypes
    }

    fun setBakeryInfo(bakeryInfo: BakeryReviewWritingInfo) {
        _bakeryInfo.value = bakeryInfo
    }

    fun writeReview() {
        viewModelScope.launch {
            reviewText.value?.let { reviewText ->
                _bakeryId.value?.let {
                    reviewWritingRepository.writeReview(
                        it,
                        RequestReviewWriting(
                            _isLike.value == LikeType.LIKE,
                            if (_isLike.value == LikeType.LIKE) getKeyWordTypeList() else emptyList(),
                            reviewText
                        )
                    )
                        .onSuccess {
                            _reviewSuccessState.value = UiState.Success(true)
                        }
                        .onFailure { throwable ->
                            _reviewSuccessState.value = UiState.Error(throwable.message)
                        }
                }
            }
        }
    }
}
