package com.sopt.geonppang.presentation.reviewWriting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.data.model.request.RequestReviewWriting
import com.sopt.geonppang.domain.repository.ReviewWritingRepository
import com.sopt.geonppang.presentation.model.BakeryReviewWritingInfo
import com.sopt.geonppang.presentation.type.KeyWordType
import com.sopt.geonppang.presentation.type.LikeType
import com.sopt.geonppang.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.set

@HiltViewModel
class ReviewWritingViewModel @Inject constructor(private val reviewWritingRepository: ReviewWritingRepository) :
    ViewModel() {

    val reviewText = MutableLiveData("")
    private val _bakeryId: MutableLiveData<Int> = MutableLiveData()
    val bakeryId: LiveData<Int> = _bakeryId
    private val _isLike: MutableLiveData<LikeType> = MutableLiveData()
    val isLike: LiveData<LikeType> = _isLike
    private val _reviewSuccessState: MutableLiveData<UiState<Boolean>> = MutableLiveData()
    val reviewSuccessState: LiveData<UiState<Boolean>> = _reviewSuccessState
    private val _reviewCancelState: MutableLiveData<Boolean> = MutableLiveData()
    val reviewCancelState: LiveData<Boolean> = _reviewCancelState
    private val _bakeryInfo: MutableLiveData<BakeryReviewWritingInfo> = MutableLiveData()
    val bakeryInfo: LiveData<BakeryReviewWritingInfo> = _bakeryInfo
    val userKeyWordType: MutableLiveData<Map<KeyWordType, Boolean>> =
        MutableLiveData(
            mapOf(
                KeyWordType.DELICIOUS to false,
                KeyWordType.KIND to false,
                KeyWordType.SPECIAL_MENU to false,
                KeyWordType.ZERO_WASTE to false
            )
        )
    val isUserKeyWordTypeSelected: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(userKeyWordType) { keyWordMap ->
            value = keyWordMap.any { it.value }
        }
    }

    fun setBakeryId(bakeryId: Int) {
        _bakeryId.value = bakeryId
    }

    fun setReviewCancelState(isCancel: Boolean) {
        _reviewCancelState.value = isCancel
    }

    fun setLikeType(isLike: LikeType) {
        _isLike.value = isLike
    }

    fun setKeyWordType(keyWordType: KeyWordType) {
        val isSelected = userKeyWordType.value?.get(keyWordType) ?: return
        userKeyWordType.value = userKeyWordType.value?.toMutableMap()?.apply {
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
