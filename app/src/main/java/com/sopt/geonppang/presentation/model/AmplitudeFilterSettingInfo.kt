package com.sopt.geonppang.presentation.model

import com.sopt.geonppang.presentation.type.BreadFilterType
import com.sopt.geonppang.presentation.type.MainPurposeType
import com.sopt.geonppang.presentation.type.NutrientFilterType

data class AmplitudeFilterSettingInfo(
    val mainPurposeType: MainPurposeType?,
    val breadType: Map<BreadFilterType, Boolean>,
    val ingredientType: Map<NutrientFilterType, Boolean>
)
