package com.sopt.geonppang.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val homeRepository: HomeRepository,
) : ViewModel() {
    private var _bestBakeryListState = MutableStateFlow<UiState<List<BestBakery>>>(UiState.Loading)
    val bestBakeryListState get() = _bestBakeryListState.asStateFlow()

    init {
        fetchBestBakeryList()
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
}
