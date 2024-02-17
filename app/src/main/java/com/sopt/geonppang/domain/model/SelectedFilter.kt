package com.sopt.geonppang.domain.model

import com.sopt.geonppang.presentation.type.BreadFilterType
import com.sopt.geonppang.presentation.type.NutrientFilterType

data class SelectedFilter(
    val mainPurpose: String,
    val breadTypeList: List<BreadFilterType>,
    val nutrientTypeList: List<NutrientFilterType>
)
