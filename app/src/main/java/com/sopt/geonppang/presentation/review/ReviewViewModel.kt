package com.sopt.geonppang.presentation.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.data.model.request.RequestWriteReview
import com.sopt.geonppang.domain.repository.WriteReviewRepository
import com.sopt.geonppang.presentation.type.KeyWordType
import com.sopt.geonppang.presentation.type.LikeType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(private val writeReviewRepository: WriteReviewRepository) :
    ViewModel() {

    val reviewText = MutableLiveData("")

    private val _isLike: MutableLiveData<LikeType> = MutableLiveData()
    val isLike: LiveData<LikeType> = _isLike

    fun setLikeType(isLike: LikeType) {
        _isLike.value = isLike
    }

    val userKeyWordType: MutableLiveData<Map<KeyWordType, Boolean>> =
        MutableLiveData(
            mapOf(
                KeyWordType.DELICIOUS to false,
                KeyWordType.KIND to false,
                KeyWordType.SPECIAL_MENU to false,
                KeyWordType.ZERO_WASTE to false
            )
        )

    fun setKeyWordType(keyWordType: KeyWordType) {
        val isSelected = userKeyWordType.value?.get(keyWordType) ?: return
        userKeyWordType.value = userKeyWordType.value?.toMutableMap()?.apply {
            this[keyWordType] = !isSelected
        }
    }

    val isUserKeyWordTypeSelected: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(userKeyWordType) { keyWordMap ->
            value = keyWordMap.any { it.value }
        }
    }

    fun getTrueKeyWordTypes(): List<RequestWriteReview.KeywordName> {
        val trueKeyWordTypes = mutableListOf<RequestWriteReview.KeywordName>()
        userKeyWordType.value?.forEach { (keyWordType, value) ->
            if (value) {
                trueKeyWordTypes.add(RequestWriteReview.KeywordName(keyWordType.keywordType))
            }
        }
        return trueKeyWordTypes
    }

    fun writeReview(bakeryId: Int) {
        viewModelScope.launch {
            reviewText.value?.let { reviewText ->
                writeReviewRepository.writeReview(
                    bakeryId, RequestWriteReview(
                        _isLike.value == LikeType.LIKE,
                        getTrueKeyWordTypes(),
                        reviewText
                    )
                )
                    .onSuccess {
                        Timber.d(it.message)
                    }
                    .onFailure {
                        Timber.e(it.message)
                    }
            }
        }
    }
}
