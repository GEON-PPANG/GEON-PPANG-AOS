package com.sopt.geonppang.presentation.bakeryList

import android.util.Log
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
    private val _personalFilter = MutableStateFlow(true)
    val personalFilter get() = _personalFilter.asStateFlow()

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

    fun setPersonalFilter() {
        _personalFilter.value = !_personalFilter.value
    }

    fun fetchBakeryList() {
        viewModelScope.launch {
            bakeryRepository.fetchBakeryList(
                bakerySort.value.sortType,
                personalFilter.value,
                bakeryCategoryType.value[BakeryCategoryType.HARD] == true,
                bakeryCategoryType.value[BakeryCategoryType.DESSERT] == true,
                bakeryCategoryType.value.get(BakeryCategoryType.BRUNCH) == true
            )
                .onSuccess { bakeryList ->
                    _bakeryListState.value = UiState.Success(bakeryList)
                    Log.d("aaaa", bakerySort.value.sortType)
                    Log.d("aaaa", personalFilter.value.toString())
                    Log.d(
                        "aaaa",
                        (bakeryCategoryType.value[BakeryCategoryType.HARD] == true).toString()
                    )
                    Log.d(
                        "aaaa",
                        (bakeryCategoryType.value[BakeryCategoryType.DESSERT] == true).toString()
                    )
                    Log.d(
                        "aaaa",
                        (bakeryCategoryType.value[BakeryCategoryType.BRUNCH] == true).toString()
                    )
                }
                .onFailure { throwable ->
                    _bakeryListState.value = UiState.Error(throwable.message)
                }
        }
    }
}
