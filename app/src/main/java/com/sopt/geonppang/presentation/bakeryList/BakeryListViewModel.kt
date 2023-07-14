package com.sopt.geonppang.presentation.bakeryList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.geonppang.presentation.type.BakerySortType

class BakeryListViewModel : ViewModel() {
    private var _bakerySort = MutableLiveData(BakerySortType.DEFAULT)
    val bakerySort get() = _bakerySort

    fun setBakerySortType(bakerySortType: BakerySortType) {
        _bakerySort.value = bakerySortType
    }
}
