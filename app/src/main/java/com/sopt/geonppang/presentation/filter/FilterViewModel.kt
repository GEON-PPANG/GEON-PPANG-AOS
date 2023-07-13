package com.sopt.geonppang.presentation.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.geonppang.domain.model.NutrientFilter
import com.sopt.geonppang.presentation.type.MainPurposeType
import com.sopt.geonppang.presentation.type.BreadFilterType
import timber.log.Timber

class FilterViewModel : ViewModel() {
    private val _nutrientTypeFilterList = listOf(
        NutrientFilter(
            title = "영양성분 공개"
        ),
        NutrientFilter(
            title = "원재료 공개"
        ),
        NutrientFilter(
            title = "비공개"
        )
    )

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

    val nutrientTypeFilterList = _nutrientTypeFilterList

//    private val _mainPurposeFilterSelectedItemIndex: MutableLiveData<Int> = MutableLiveData()
//    val mainPurposeFilterSelectedItemIndex: LiveData<Int> = _mainPurposeFilterSelectedItemIndex
//
//    private val _breadTypeFilterSelectedItemList: MutableLiveData<List<Int>> = MutableLiveData()
//    val breadTypeFilterSelectedItemList: LiveData<List<Int>> = _breadTypeFilterSelectedItemList

    private val _nutrientTypeFilterSelectedItemList: MutableLiveData<List<Int>> = MutableLiveData()
    val nutrientTypeFilterSelectedItemList: LiveData<List<Int>> =
        _nutrientTypeFilterSelectedItemList

    private val _isNextBtnEnabled: MutableLiveData<Boolean> = MutableLiveData()
    val isNextBtnEnabled: LiveData<Boolean> = _isNextBtnEnabled

//    fun setMainPurposeFilterSelectedItemIndex(index: Int) {
//        _mainPurposeFilterSelectedItemIndex.value = index
//        _isNextBtnEnabled.value = (index != -1)
//    }

    fun setIsNextBtnEnabled(value: Boolean) {
        _isNextBtnEnabled.value = value
    }

//    fun setBreadTypeFilterSelectedItemList(index: Int) {
//        val currentList = _breadTypeFilterSelectedItemList.value.orEmpty().toMutableList()
//
//        if (currentList.contains(index)) currentList.remove(index)
//        else {
//            currentList.add(index)
//            currentList.sort()
//        }
//
//        _breadTypeFilterSelectedItemList.value = currentList
//        _isNextBtnEnabled.value = (!currentList.isEmpty())
//    }

    fun setNutrientTypeFilterSelectedItemList(index: Int) {
        val currentList = _nutrientTypeFilterSelectedItemList.value.orEmpty().toMutableList()

        if (currentList.contains(index)) currentList.remove(index)
        else {
            currentList.add(index)
            currentList.sort()
        }

        _nutrientTypeFilterSelectedItemList.value = currentList
        _isNextBtnEnabled.value = (!currentList.isEmpty())
        Timber.e(nutrientTypeFilterSelectedItemList.value.toString())
    }
}
