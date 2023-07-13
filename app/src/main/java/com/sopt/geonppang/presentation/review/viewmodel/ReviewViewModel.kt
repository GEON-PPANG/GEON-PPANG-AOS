package com.sopt.geonppang.presentation.review.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.domain.repository.DummyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val dummyRepository: DummyRepository,
) : ViewModel() {

    val review_text = MutableLiveData("")
    val reveiw_text_count = MutableLiveData("")

    val countReviewText: LiveData<Int> = review_text.map { review_text ->
        review_text.length
        /*글자 수 세기*/
    }

    val countReviewTextTrue: LiveData<Boolean> = review_text.map { review_text ->
        review_text.length >= 10
        /*10글자 이상하면 저장 버튼 활성화*/
    }

    /*서버에서 주는 정보에 따라 사진의 변화*/

    fun uploadDummy() {
        viewModelScope.launch {
            dummyRepository.uploadDummy("name", "dummy")
                .onSuccess {
                }
                .onFailure {
                }
        }
    }
}
