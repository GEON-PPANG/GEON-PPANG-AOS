package com.sopt.geonppang.presentation.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.geonppang.presentation.type.LikeType

class ReviewViewModel : ViewModel() {

    val reviewText = MutableLiveData("")

    private val _isLike: MutableLiveData<LikeType> = MutableLiveData()
    val isLike: LiveData<LikeType> = _isLike

    fun setLikeType(isLike: LikeType) {
        _isLike.value = isLike
    }
}
