package com.sopt.geonppang.presentation.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.data.datasource.local.GPDataStore
import com.sopt.geonppang.data.model.request.RequestSettingFilter
import com.sopt.geonppang.data.model.request.RequestFilter
import com.sopt.geonppang.domain.model.SelectedFilter
import com.sopt.geonppang.domain.repository.FilterSettingRepository
import com.sopt.geonppang.presentation.type.BreadFilterType
import com.sopt.geonppang.presentation.type.FilterInfoType
import com.sopt.geonppang.presentation.type.MainPurposeType
import com.sopt.geonppang.presentation.type.NutrientFilterType
import com.sopt.geonppang.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterSettingViewModel @Inject constructor(
    private val filterRepository: FilterSettingRepository,
    private val gpDataStore: GPDataStore
) : ViewModel() {
    private val _selectedFilterState = MutableStateFlow<UiState<SelectedFilter>>(UiState.Loading)
    val selectedFilterState get() = _selectedFilterState.asStateFlow()

    private val _previousActivity = MutableStateFlow<FilterInfoType?>(null)
    val previousActivity get() = _previousActivity.asStateFlow()

    private val _isLastPage = MutableStateFlow(false)
    val isLastPage get() = _isLastPage.asStateFlow()

    fun setIsLastPage(boolean: Boolean) {
        _isLastPage.value = boolean
    }

    private val _currentItem: MutableLiveData<Int> = MutableLiveData()
    val currentItem: LiveData<Int> = _currentItem

    fun setCurrentItem(position: Int) {
        _currentItem.value = position
    }

    private val _mainPurpose: MutableLiveData<MainPurposeType?> = MutableLiveData()
    val mainPurpose: LiveData<MainPurposeType?> = _mainPurpose

    fun setMainPurpose(mainPurposeType: MainPurposeType) {
        _mainPurpose.value = mainPurposeType
    }

    val breadFilterType: MutableLiveData<Map<BreadFilterType, Boolean>> = MutableLiveData(
        mapOf(
            BreadFilterType.GLUTENFREE to false,
            BreadFilterType.VEGAN to false,
            BreadFilterType.NUTFREE to false,
            BreadFilterType.SUGARFREE to false
        )
    )

    fun setUserBreadType(breadType: BreadFilterType) {
        val isSelected = breadFilterType.value?.get(breadType) ?: return
        breadFilterType.value = breadFilterType.value?.toMutableMap()?.apply {
            this[breadType] = !isSelected
        }
    }

    val isUserBreadTypeSelected: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(breadFilterType) { breadMap ->
            value = breadMap.any { it.value }
        }
    }

    val nutrientFilterType: MutableLiveData<Map<NutrientFilterType, Boolean>> = MutableLiveData(
        mapOf(
            NutrientFilterType.NUTRIENT to false,
            NutrientFilterType.INGREDIENT to false,
            NutrientFilterType.NOT to false
        )
    )

    fun setNutrientType(nutrientType: NutrientFilterType) {
        val isSelected = nutrientFilterType.value?.get(nutrientType) ?: return
        nutrientFilterType.value = nutrientFilterType.value?.toMutableMap()?.apply {
            this[nutrientType] = !isSelected
        }
    }

    val isUserNutrientFilterTypeSelected: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(nutrientFilterType) { nutrientMap ->
            value = nutrientMap.any { it.value }
        }
    }

    val currentItemFilterSelected: MediatorLiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(currentItem) { currentItemValue ->
            value = when (currentItemValue) {
                0 -> mainPurpose.value != null
                1 -> breadFilterType.value?.any { it.value } ?: false
                2 -> nutrientFilterType.value?.any { it.value } ?: false
                else -> false
            }
        }
    }

    fun setPreviousActivity(filterInfoType: FilterInfoType) {
        _previousActivity.value = filterInfoType
    }

    fun setUserFilter() {
        viewModelScope.launch {
            _mainPurpose.value?.let {
                RequestSettingFilter(
                    it.name,
                    RequestSettingFilter.BreadType(
                        breadFilterType.value?.get(BreadFilterType.GLUTENFREE) == true,
                        breadFilterType.value?.get(BreadFilterType.VEGAN) == true,
                        breadFilterType.value?.get(BreadFilterType.NUTFREE) == true,
                        breadFilterType.value?.get(BreadFilterType.SUGARFREE) == true,
                    ),
                    RequestSettingFilter.NutrientType(
                        nutrientFilterType.value?.get(NutrientFilterType.NUTRIENT) == true,
                        nutrientFilterType.value?.get(NutrientFilterType.INGREDIENT) == true,
                        nutrientFilterType.value?.get(NutrientFilterType.NOT) == true,
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
