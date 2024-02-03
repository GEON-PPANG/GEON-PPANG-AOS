package com.sopt.geonppang.data.model.response

import com.sopt.geonppang.domain.model.SelectedFilter
import kotlinx.serialization.Serializable

@Serializable
data class ResponseSettingFilter(
    val code: Int,
    val data: Data,
    val message: String
) {
    @Serializable
    data class Data(
        val breadTypeList: List<BreadTypeId>,
        val mainPurpose: String,
        val memberId: Int,
        val nickname: String,
        val nutrientTypeList: List<NutrientTypeId>
    )

    @Serializable
    data class BreadTypeId(
        val breadTypeId: Int
    )

    @Serializable
    data class NutrientTypeId(
        val nutrientTypeId: Int
    )

    fun toSelectedFilter() = SelectedFilter(
        mainPurpose = data.mainPurpose,
        breadTypeList = data.breadTypeList.map { breadType -> SelectedFilter.BreadTypeId(breadTypeId = breadType.breadTypeId) },
        nutrientTypeList = data.nutrientTypeList.map { nutrientType ->
            SelectedFilter.NutrientTypeId(
                nutrientTypeId = nutrientType.nutrientTypeId
            )
        }
    )
}
