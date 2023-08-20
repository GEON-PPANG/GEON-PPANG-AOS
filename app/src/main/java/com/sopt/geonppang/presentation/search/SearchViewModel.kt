package com.sopt.geonppang.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.domain.model.Search
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

    private var _searchState = MutableStateFlow<UiState<Search>>(UiState.Loading)
    val searchState get() = _searchState.asStateFlow()

    fun searchBakeryList() {
        viewModelScope.launch {
            searchRepository.searchBakery(inputSearch.value)
                .onSuccess { searchBakery ->
                    _searchCount.value = searchBakery.bakeryList.size
                    _searchState.value = UiState.Success(searchBakery)
                }
                .onFailure { throwable ->
                    _searchState.value = UiState.Error(throwable.message)
                }
        }
    }
}
