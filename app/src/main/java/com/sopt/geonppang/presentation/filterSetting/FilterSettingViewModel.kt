package com.sopt.geonppang.presentation.filterSetting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.data.datasource.local.GPDataSource
import com.sopt.geonppang.data.model.request.RequestSettingFilter
import com.sopt.geonppang.domain.repository.FilterSettingRepository
import com.sopt.geonppang.presentation.model.AmplitudeFilterSettingInfo
import com.sopt.geonppang.presentation.type.BreadFilterType
import com.sopt.geonppang.presentation.type.FilterInfoType
import com.sopt.geonppang.presentation.type.MainPurposeFilterType
import com.sopt.geonppang.presentation.type.NutrientFilterType
import com.sopt.geonppang.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterSettingViewModel @Inject constructor(
    gpDataSource: GPDataSource,
    private val filterRepository: FilterSettingRepository
) : ViewModel() {
    private val _selectedFilterState =
        MutableStateFlow<UiState<AmplitudeFilterSettingInfo>>(UiState.Loading)
    val selectedFilterState get() = _selectedFilterState.asStateFlow()
    private val _previousActivity = MutableStateFlow<FilterInfoType?>(null)
    val previousActivity get() = _previousActivity.asStateFlow()
    private val _currentPage = MutableStateFlow<Int?>(null)
    val currentPage get() = _currentPage.asStateFlow()
    private val _mainPurposeType = MutableStateFlow<MainPurposeFilterType?>(null)
    val mainPurposeType get() = _mainPurposeType.asStateFlow()
    val breadFilterTypeList: MutableStateFlow<List<Int>> = MutableStateFlow(emptyList())
    private val _nutrientFilterType = MutableStateFlow<NutrientFilterType?>(null)
    val nutrientFilterType get() = _nutrientFilterType.asStateFlow()
    val isFilterBtnEnabled: StateFlow<Boolean> = combine(
        currentPage,
        mainPurposeType,
        breadFilterTypeList,
        nutrientFilterType
    ) { currentPage, mainPurposeType, breadFilterType, nutrientFilterType ->
        when (currentPage) {
            0 -> mainPurposeType != null
            1 -> breadFilterType.isNotEmpty()
            2 -> nutrientFilterType != null
            else -> false
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val platformType = gpDataSource.platformType

    fun setPreviousActivity(filterInfoType: FilterInfoType) {
        _previousActivity.value = filterInfoType
    }

    fun setCurrentPage(position: Int) {
        _currentPage.value = position
    }

    fun setMainPurposeType(mainPurposeType: MainPurposeFilterType) {
        _mainPurposeType.value = mainPurposeType
    }

    fun setBreadFilterType(breadType: BreadFilterType) {
        breadFilterTypeList.value = breadFilterTypeList.value.toMutableList().apply {
            if (!remove(breadType.id)) add(breadType.id)
            sort()
        }
    }

    fun setNutrientFilterType(nutrientType: NutrientFilterType) {
        _nutrientFilterType.value = nutrientType
    }

    fun setUserFilter() {
        viewModelScope.launch {
            _mainPurposeType.value?.let { mainPurposeFilterType ->
                _nutrientFilterType.value?.let { nutrientFilterType ->
                    RequestSettingFilter(
                        mainPurpose = mainPurposeFilterType.name,
                        breadTypeList = breadFilterTypeList.value,
                        nutrientTypeList = listOf(nutrientFilterType.id)
                    )
                }
            }?.let { requestSettingFilter ->
                filterRepository.setUserFilter(requestSettingFilter)
                    .onSuccess {
                        _selectedFilterState.value = UiState.Success(
                            AmplitudeFilterSettingInfo(
                                mainPurposeType = _mainPurposeType.value,
                                breadType = breadFilterTypeList.value.mapNotNull { breadFilterTypeId ->
                                    BreadFilterType.values()
                                        .find { it.id == breadFilterTypeId }?.name
                                },
                                ingredientType = _nutrientFilterType.value,
                            )
                        )
                    }
                    .onFailure { throwable ->
                        _selectedFilterState.value = UiState.Error(throwable.message)
                    }
            }
        }
    }
}
