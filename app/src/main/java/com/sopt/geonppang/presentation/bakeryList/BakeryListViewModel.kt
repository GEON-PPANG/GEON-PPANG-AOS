package com.sopt.geonppang.presentation.bakeryList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sopt.geonppang.data.repository.BakeryListPagingRepository
import com.sopt.geonppang.domain.model.BakeryInformation
import com.sopt.geonppang.domain.model.BakeryListFilterType
import com.sopt.geonppang.domain.repository.GetUserFilterRepository
import com.sopt.geonppang.presentation.type.BakeryCategoryType
import com.sopt.geonppang.presentation.type.BakerySortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BakeryListViewModel @Inject constructor(
    private val bakeryListPagingRepository: BakeryListPagingRepository,
    private val getUserFilterRepository: GetUserFilterRepository
) : ViewModel() {
    private var _bakeryListFilterState = MutableStateFlow(BakeryListFilterType())
    val bakeryListFilterType get() = _bakeryListFilterState.asStateFlow()

    private val _isFilterSelected = MutableStateFlow(true)
    val isFilterSelected get() = _isFilterSelected.asStateFlow()

    fun setBakerySortType(bakerySortType: BakerySortType) {
        _bakeryListFilterState.update {
            it.copy(sortType = bakerySortType)
        }
    }

    // TODO: dana update 로직 수정 필요
    fun setIsPersonalFilterAppliedState() {
        _bakeryListFilterState.update {
            it.copy(isPersonalFilterApplied = it.isPersonalFilterApplied == false)
        }
    }

    fun setBakeryCategoryType(bakeryCategory: BakeryCategoryType) {
        _bakeryListFilterState.update { bakeryListFilterState ->
            when (bakeryCategory) {
                BakeryCategoryType.HARD -> bakeryListFilterState.copy(isHard = !bakeryListFilterState.isHard)
                BakeryCategoryType.DESSERT -> bakeryListFilterState.copy(isDessert = !bakeryListFilterState.isDessert)
                BakeryCategoryType.BRUNCH -> bakeryListFilterState.copy(isBrunch = !bakeryListFilterState.isBrunch)
            }
        }
    }

    // TODO: dana 필터 적용 상태(앱 내 유저 상태) 어떻게 관리할 건지 고민
    private fun setInitPersonalFilterState() {
        _bakeryListFilterState.update {
            it.copy(isPersonalFilterApplied = _isFilterSelected.value)
        }
    }

    fun fetchBakeryListPagingData(): Flow<PagingData<BakeryInformation>> {
        return bakeryListPagingRepository.fetchBakeryList(
            bakeryListFilterType = bakeryListFilterType.value
        ).cachedIn(viewModelScope)
    }

    fun getUserFilter() {
        viewModelScope.launch {
            getUserFilterRepository.getUserFilter()
                .onSuccess { userFilterInfo ->
                    _isFilterSelected.value = (userFilterInfo.mainPurpose != NONE)
                    setInitPersonalFilterState()
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
