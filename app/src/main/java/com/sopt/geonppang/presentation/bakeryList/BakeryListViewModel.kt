package com.sopt.geonppang.presentation.bakeryList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.domain.model.Bakery
import com.sopt.geonppang.domain.repository.BakeryRepository
import com.sopt.geonppang.domain.repository.GetUserFilterRepository
import com.sopt.geonppang.presentation.type.BakeryCategoryType
import com.sopt.geonppang.presentation.type.BakerySortType
import com.sopt.geonppang.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BakeryListViewModel @Inject constructor(
    private val bakeryRepository: BakeryRepository,
    private val getUserFilterRepository: GetUserFilterRepository
) : ViewModel() {
    private var _bakerySort = MutableStateFlow(BakerySortType.DEFAULT)
    val bakerySort get() = _bakerySort.asStateFlow()
    private val _isPersonalFilterApplied = MutableStateFlow<Boolean?>(null)
    val isPersonalFilterApplied get() = _isPersonalFilterApplied.asStateFlow()

    private var _bakeryListState = MutableStateFlow<UiState<List<Bakery>>>(UiState.Loading)
    val bakeryListState get() = _bakeryListState.asStateFlow()

    val bakeryCategoryType: MutableStateFlow<Map<BakeryCategoryType, Boolean>> = MutableStateFlow(
        mapOf(
            BakeryCategoryType.HARD to false,
            BakeryCategoryType.DESSERT to false,
            BakeryCategoryType.BRUNCH to false,
        )
    )
    private val _isFilterSelected = MutableStateFlow(false)
    val isFilterSelected get() = _isFilterSelected.asStateFlow()

    init {
        getUserFilter()
        setInitPersonalFilterState()
    }

    fun setBakerySortType(bakerySortType: BakerySortType) {
        _bakerySort.value = bakerySortType
    }

    fun setPersonalFilter() {
        _isPersonalFilterApplied.value?.let { isPersonalFilterApplied ->
            _isPersonalFilterApplied.value = !isPersonalFilterApplied
        }
    }

    private fun setInitPersonalFilterState() {
        _isPersonalFilterApplied.value = isFilterSelected.value
    }

    fun fetchBakeryList() {
        viewModelScope.launch {
            isPersonalFilterApplied.value?.let { isPersonalFilterApplied ->
                bakeryRepository.fetchBakeryList(
                    bakerySort.value.sortType,
                    isPersonalFilterApplied,
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

    fun setBakeryCategoryType(bakeryCategory: BakeryCategoryType) {
        val isChecked = bakeryCategoryType.value[bakeryCategory] ?: return
        bakeryCategoryType.value = bakeryCategoryType.value.toMutableMap().apply {
            this[bakeryCategory] = !isChecked
        }
    }

    private fun getUserFilter() {
        viewModelScope.launch {
            getUserFilterRepository.getUserFilter()
                .onSuccess { userFilterInfo ->
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
