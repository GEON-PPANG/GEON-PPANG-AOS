package com.sopt.geonppang.domain.model

data class SelectedFilter(
    val mainPurpose: String,
    val breadTypeList: List<BreadTypeId>,
    val nutrientTypeList: List<NutrientTypeId>
) {
    data class BreadTypeId(
        val breadTypeId: Int
    )

    data class NutrientTypeId(
        val nutrientTypeId: Int
    )
}
