package com.sopt.geonppang.presentation.filterSetting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.data.model.request.RequestSettingFilter
import com.sopt.geonppang.domain.model.SelectedFilter
import com.sopt.geonppang.domain.repository.FilterSettingRepository
import com.sopt.geonppang.presentation.type.BreadFilterType
import com.sopt.geonppang.presentation.type.FilterInfoType
import com.sopt.geonppang.presentation.type.MainPurposeType
import com.sopt.geonppang.presentation.type.NutrientFilterType
import com.sopt.geonppang.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterSettingViewModel @Inject constructor(
    private val filterRepository: FilterSettingRepository
) : ViewModel() {
    private val _selectedFilterState = MutableStateFlow<UiState<SelectedFilter>>(UiState.Loading)
    val selectedFilterState get() = _selectedFilterState.asStateFlow()
    private val _previousActivity = MutableStateFlow<FilterInfoType?>(null)
    val previousActivity get() = _previousActivity.asStateFlow()
    private val _currentPage = MutableStateFlow<Int?>(null)
    val currentPage get() = _currentPage.asStateFlow()
    private val _mainPurposeType = MutableStateFlow<MainPurposeType?>(null)
    val mainPurposeType get() = _mainPurposeType.asStateFlow()
    val breadFilterType: MutableStateFlow<Map<BreadFilterType, Boolean>> = MutableStateFlow(
        mapOf(
            BreadFilterType.GLUTENFREE to false,
            BreadFilterType.VEGAN to false,
            BreadFilterType.NUTFREE to false,
            BreadFilterType.SUGARFREE to false
        )
    )
    val isBreadFilterTypeSelected: StateFlow<Boolean> = breadFilterType.map { breadTypeMap ->
        breadTypeMap.values.any { it }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)
    val nutrientFilterType: MutableStateFlow<Map<NutrientFilterType, Boolean>> = MutableStateFlow(
        mapOf(
            NutrientFilterType.NUTRIENT to false,
            NutrientFilterType.INGREDIENT to false,
            NutrientFilterType.NOT to false
        )
    )
    val isNutrientFilterTypeSelected: StateFlow<Boolean> =
        nutrientFilterType.map { nutrientFilterType ->
            nutrientFilterType.values.any { it }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)
    val isCurrentPageFilterSelected: StateFlow<Boolean> = _currentPage.map { currentPage ->
        when (currentPage) {
            0 -> mainPurposeType.value != null
            1 -> breadFilterType.value.any { it.value }
            2 -> nutrientFilterType.value.any { it.value }
            else -> false
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    fun setPreviousActivity(filterInfoType: FilterInfoType) {
        _previousActivity.value = filterInfoType
    }

    fun setCurrentPage(position: Int) {
        _currentPage.value = position
    }

    fun setMainPurposeType(mainPurposeType: MainPurposeType) {
        _mainPurposeType.value = mainPurposeType
    }

    fun setBreadFilterType(breadType: BreadFilterType) {
        val isSelected = breadFilterType.value[breadType] ?: return
        breadFilterType.value = breadFilterType.value.toMutableMap().apply {
            this[breadType] = !isSelected
        }
    }

    fun setNutrientFilterType(nutrientType: NutrientFilterType) {
        val isSelected = nutrientFilterType.value[nutrientType] ?: return
        nutrientFilterType.value = nutrientFilterType.value.toMutableMap().apply {
            this[nutrientType] = !isSelected
        }
    }

    fun setUserFilter() {
        viewModelScope.launch {
            _mainPurposeType.value?.let {
                RequestSettingFilter(
                    it.name,
                    RequestSettingFilter.BreadType(
                        breadFilterType.value[BreadFilterType.GLUTENFREE] == true,
                        breadFilterType.value[BreadFilterType.VEGAN] == true,
                        breadFilterType.value[BreadFilterType.NUTFREE] == true,
                        breadFilterType.value[BreadFilterType.SUGARFREE] == true,
                    ),
                    RequestSettingFilter.NutrientType(
                        nutrientFilterType.value[NutrientFilterType.NUTRIENT] == true,
                        nutrientFilterType.value[NutrientFilterType.INGREDIENT] == true,
                        nutrientFilterType.value[NutrientFilterType.NOT] == true,
                    )
                )
            }?.let {
                filterRepository.setUserFilter(
                    it
                )
                    .onSuccess { selectedFilter ->
                        _selectedFilterState.value = UiState.Success(selectedFilter)
                    }
                    .onFailure { throwable ->
                        _selectedFilterState.value = UiState.Error(throwable.message)
                    }
            }
        }
    }
}
