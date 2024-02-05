package com.sopt.geonppang.presentation.model

import com.sopt.geonppang.presentation.type.BreadFilterType
import com.sopt.geonppang.presentation.type.MainPurposeFilterType
import com.sopt.geonppang.presentation.type.NutrientFilterType

data class AmplitudeFilterSettingInfo(
    val mainPurposeType: MainPurposeFilterType?,
    val breadType: List<String>,
    val ingredientType: NutrientFilterType?
)
