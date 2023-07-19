package com.sopt.geonppang.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.domain.model.Bakery
import com.sopt.geonppang.domain.repository.SearchRepository
import com.sopt.geonppang.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
) : ViewModel() {
    val inputSearch = MutableStateFlow("")

    private var _searchCount = MutableStateFlow<Int?>(null)
    val searchCount get() = _searchCount.asStateFlow()

    private var _searchBakeryListState = MutableStateFlow<UiState<List<Bakery>>>(UiState.Loading)
    val searchBakeryListState get() = _searchBakeryListState.asStateFlow()

    fun searchBakeryList() {
        viewModelScope.launch {
            searchRepository.searchBakery(inputSearch.value)
                .onSuccess { searchBakeryList ->
                    _searchCount.value = searchBakeryList.size
                    _searchBakeryListState.value = UiState.Success(searchBakeryList)
                }
                .onFailure { throwable ->
                    _searchBakeryListState.value = UiState.Error(throwable.message)
                }
        }
    }
}
