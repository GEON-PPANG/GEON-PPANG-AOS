package com.sopt.geonppang.presentation.bakeryList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.domain.model.Bakery
import com.sopt.geonppang.domain.repository.BakeryRepository
import com.sopt.geonppang.presentation.type.BakeryCategoryType
import com.sopt.geonppang.presentation.type.BakerySortType
import com.sopt.geonppang.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BakeryListViewModel @Inject constructor(
    private val bakeryRepository: BakeryRepository,
) : ViewModel() {
    private var _bakerySort = MutableStateFlow(BakerySortType.DEFAULT)
    val bakerySort get() = _bakerySort.asStateFlow()

    private var _bakeryListState = MutableStateFlow<UiState<List<Bakery>>>(UiState.Loading)
    val bakeryListState get() = _bakeryListState.asStateFlow()

    val bakeryCategoryType: MutableStateFlow<Map<BakeryCategoryType, Boolean>> = MutableStateFlow(
        mapOf(
            BakeryCategoryType.HARD to false,
            BakeryCategoryType.DESSERT to false,
            BakeryCategoryType.BRUNCH to false,
        )
    )

    init {
        fetchBakeryList()
    }

    fun setBakeryCategoryType(bakeryCategory: BakeryCategoryType) {
        val isChecked = bakeryCategoryType.value[bakeryCategory] ?: return
        bakeryCategoryType.value = bakeryCategoryType.value.toMutableMap().apply {
            this[bakeryCategory] = !isChecked
        }
    }

    fun setBakerySortType(bakerySortType: BakerySortType) {
        _bakerySort.value = bakerySortType
    }

    fun fetchBakeryList() {
        viewModelScope.launch {
            bakeryRepository.fetchBakeryList(
                bakerySort.value.sortType,
                bakeryCategoryType.value[BakeryCategoryType.HARD] == true,
                bakeryCategoryType.value[BakeryCategoryType.DESSERT] == true,
                bakeryCategoryType.value.get(BakeryCategoryType.BRUNCH) == true
            )
                .onSuccess { bakeryList ->
                    _bakeryListState.value = UiState.Success(bakeryList)
                }
                .onFailure { throwable ->
                    _bakeryListState.value = UiState.Error(throwable.message)
                }
        }
    }
}
