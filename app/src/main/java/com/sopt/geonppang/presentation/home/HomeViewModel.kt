package com.sopt.geonppang.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.domain.model.BestBakery
import com.sopt.geonppang.domain.model.BestReview
import com.sopt.geonppang.domain.repository.GetUserFilterRepository
import com.sopt.geonppang.domain.repository.HomeRepository
import com.sopt.geonppang.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val getUserFilterRepository: GetUserFilterRepository
) : ViewModel() {
    private var _bestBakeryListState = MutableStateFlow<UiState<List<BestBakery>>>(UiState.Loading)
    val bestBakeryListState get() = _bestBakeryListState.asStateFlow()

    private var _bestReviewListState = MutableStateFlow<UiState<List<BestReview>>>(UiState.Loading)
    val bestReviewListState get() = _bestReviewListState.asStateFlow()
    private val _nickName = MutableStateFlow("")
    val nickName get() = _nickName.asStateFlow()
    private val _isFilterSelected = MutableStateFlow(true)
    val isFilterSelected get() = _isFilterSelected.asStateFlow()

    init {
        getUserFilter()
    }

    fun fetchBestBakeryList() {
        viewModelScope.launch {
            homeRepository.fetchBestBakery()
                .onSuccess { bestBakeryList ->
                    _bestBakeryListState.value = UiState.Success(bestBakeryList)
                    fetchBestReviewList()
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

    private fun getUserFilter() {
        viewModelScope.launch {
            getUserFilterRepository.getUserFilter()
                .onSuccess { userFilterInfo ->
                    _nickName.value = userFilterInfo.nickName
                    _isFilterSelected.value = (userFilterInfo.mainPurpose != NONE)
                }
                .onFailure { throwable ->
                    Timber.e(throwable.message)
                }
        }
    }

    companion object {
        const val NONE = "NONE"
    }
}
