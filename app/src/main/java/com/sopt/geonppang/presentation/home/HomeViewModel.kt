package com.sopt.geonppang.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.data.datasource.local.GPDataStore
import com.sopt.geonppang.domain.model.BestBakery
import com.sopt.geonppang.domain.model.BestReview
import com.sopt.geonppang.domain.repository.HomeRepository
import com.sopt.geonppang.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val gpDataStore: GPDataStore,
    private val homeRepository: HomeRepository,
) : ViewModel() {
    private var _bestBakeryListState = MutableStateFlow<UiState<List<BestBakery>>>(UiState.Loading)
    val bestBakeryListState get() = _bestBakeryListState.asStateFlow()

    private var _bestReviewListState = MutableStateFlow<UiState<List<BestReview>>>(UiState.Loading)
    val bestReviewListState get() = _bestReviewListState.asStateFlow()

    private val _nickName = MutableStateFlow<String?>(null)
    val nickName get() = _nickName.asStateFlow()

    init {
        fetchBestBakeryList()
        fetchBestReviewList()
    }

    fun setUserNickName() {
        _nickName.value = gpDataStore.userNickname
    }

    private fun fetchBestBakeryList() {
        viewModelScope.launch {
            homeRepository.fetchBestBakery()
                .onSuccess { bestBakeryList ->
                    _bestBakeryListState.value = UiState.Success(bestBakeryList)
                }
                .onFailure { throwable ->
                    _bestBakeryListState.value = UiState.Error(throwable.message)
                }
        }
    }

    private fun fetchBestReviewList() {
        viewModelScope.launch {
            homeRepository.fetchBestReview()
                .onSuccess { bestReviewList ->
                    _bestReviewListState.value = UiState.Success(bestReviewList)
                }
                .onFailure { throwable ->
                    _bestReviewListState.value = UiState.Error(throwable.message)
                }
        }
    }
}
