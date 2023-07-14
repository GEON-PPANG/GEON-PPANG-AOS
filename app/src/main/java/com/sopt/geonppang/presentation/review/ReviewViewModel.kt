package com.sopt.geonppang.presentation.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.geonppang.presentation.type.KeyWordType
import com.sopt.geonppang.presentation.type.LikeType

class ReviewViewModel : ViewModel() {

    val reviewText = MutableLiveData("")

    private val _isLike: MutableLiveData<LikeType> = MutableLiveData()
    val isLike: LiveData<LikeType> = _isLike

    fun setLikeType(isLike: LikeType) {
        _isLike.value = isLike
    }

    val userKeyWordType: MutableLiveData<Map<KeyWordType, Boolean>> =
        MutableLiveData(
            mapOf(
                KeyWordType.TASTE to false,
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
}
