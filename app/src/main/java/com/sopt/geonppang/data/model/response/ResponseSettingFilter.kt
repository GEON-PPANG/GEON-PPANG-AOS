package com.sopt.geonppang.data.model.response

import com.sopt.geonppang.domain.model.SelectedFilter
import com.sopt.geonppang.presentation.type.BreadFilterType
import com.sopt.geonppang.presentation.type.NutrientFilterType
import kotlinx.serialization.Serializable

@Serializable
data class ResponseSettingFilter(
    val code: Int,
    val data: Data,
    val message: String
) {
    @Serializable
    data class Data(
        val breadTypeList: List<BreadTypeIdDto>,
        val mainPurpose: String,
        val memberId: Int,
        val nickname: String,
        val nutrientTypeList: List<NutrientTypeIdDto>
    )

    fun toSelectedFilter() = SelectedFilter(
        mainPurpose = data.mainPurpose,
        breadTypeList = data.breadTypeList.mapNotNull { breadType ->
            BreadFilterType.values().find {
                it.id == breadType.breadTypeId
            }
        },
        nutrientTypeList = data.nutrientTypeList.mapNotNull { nutrientTypeIdDto ->
            NutrientFilterType.values().find {
                it.id == nutrientTypeIdDto.nutrientTypeId
            }
        }
    )
}
