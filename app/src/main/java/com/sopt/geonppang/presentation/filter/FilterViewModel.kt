package com.sopt.geonppang.presentation.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.geonppang.domain.model.Filter

class FilterViewModel : ViewModel() {
    private val _mainPurposeFilterList = listOf(
        Filter(
            title = "건강 · 체질",
            detail = "아토피 , 알레르기 , 암 , 당뇨 , 소화문제"
        ),
        Filter(
            title = "맛 · 다이어트",
            detail = "그냥 맛있어서! 식이 관리를 위해"
        ),
        Filter(
            title = "비건 · 채식지향",
            detail = "종교 , 환경 , 동물 , 노동권을 위한 비거니즘"
        )
    )
    val mainPurposeFilterList = _mainPurposeFilterList

    private val _breadTypeFilterList = listOf(
        Filter(
            title = "글루텐프리",
            detail = "NO 글루텐 포함\n밀 , 곡물류"
        ),
        Filter(
            title = "비건빵",
            detail = "NO 동물성재료\n(유제품 , 계란)"
        ),
        Filter(
            title = "넛프리",
            detail = "NO 견과류"
        ),
        Filter(
            title = "저당 , 무설탕",
            detail = "대체당 사용"
        )
    )
    val breadTypeFilterList = _breadTypeFilterList

    private val _mainPurposeFilterSelectedItemIndex: MutableLiveData<Int> = MutableLiveData(-1)
    val mainPurposeFilterSelectedItemIndex: LiveData<Int> = _mainPurposeFilterSelectedItemIndex

    val _isNextBtnEnabled: MutableLiveData<Boolean> = MutableLiveData()
    val isNextBtnEnabled: LiveData<Boolean> = _isNextBtnEnabled

    fun setMainPurposeFilterSelectedItemIndex(index: Int) {
        _mainPurposeFilterSelectedItemIndex.value = index
        _isNextBtnEnabled.value = (index != -1)
    }
}
