package com.sopt.geonppang.presentation.bakeryList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sopt.geonppang.data.datasource.local.GPDataSource
import com.sopt.geonppang.data.repository.BakeryListPagingRepository
import com.sopt.geonppang.domain.model.BakeryInformation
import com.sopt.geonppang.domain.model.BakeryListFilterType
import com.sopt.geonppang.presentation.type.BakeryCategoryType
import com.sopt.geonppang.presentation.type.BakerySortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BakeryListViewModel @Inject constructor(
    private val bakeryListPagingRepository: BakeryListPagingRepository,
    private val gpDataSource: GPDataSource
) : ViewModel() {
    private var _bakeryListFilterState = MutableStateFlow(BakeryListFilterType())
    val bakeryListFilterType get() = _bakeryListFilterState.asStateFlow()

    private val _userRoleType = MutableStateFlow(gpDataSource.userRoleType)
    val userRoleType get() = _userRoleType

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

    fun fetchBakeryListPagingData(): Flow<PagingData<BakeryInformation>> {
        return bakeryListPagingRepository.fetchBakeryList(
            bakeryListFilterType = bakeryListFilterType.value
        ).cachedIn(viewModelScope)
    }

    companion object {
        const val NONE = "NONE"
    }
}
