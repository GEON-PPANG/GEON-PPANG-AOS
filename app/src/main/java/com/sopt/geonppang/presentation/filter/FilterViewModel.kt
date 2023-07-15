package com.sopt.geonppang.presentation.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.geonppang.presentation.type.BreadFilterType
import com.sopt.geonppang.presentation.type.MainPurposeType
import com.sopt.geonppang.presentation.type.NutrientFilterType

class FilterViewModel : ViewModel() {
    // 현재 선택한 mainPurpose를 가지고 있는 LiveeData
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
}
