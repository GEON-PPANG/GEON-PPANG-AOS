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
        val breadType: BreadType,
        val mainPurpose: String,
        val memberId: Int,
        val nickname: String,
        val nutrientType: NutrientType
    )

    @Serializable
    data class BreadType(
        val breadTypeId: Int,
        val breadTypeName: String,
        val isGlutenFree: Boolean,
        val isNutFree: Boolean,
        val isSugarFree: Boolean,
        val isVegan: Boolean
    )

    @Serializable
    data class NutrientType(
        val isIngredientOpen: Boolean,
        val isNotOpen: Boolean,
        val isNutrientOpen: Boolean,
        val nutrientTypeId: Int,
        val nutrientTypeName: String
    )

    fun toSelectedFilter() = SelectedFilter(
        mainPurpose = data.mainPurpose,
        breadType = SelectedFilter.BreadType(
            isGlutenFree = data.breadType.isGlutenFree,
            isVegan = data.breadType.isVegan,
            isNutFree = data.breadType.isNutFree,
            isSugarFree = data.breadType.isSugarFree
        ),
        nutrientType = SelectedFilter.NutrientType(
            isIngredientOpen = data.nutrientType.isIngredientOpen,
            isNotOpen = data.nutrientType.isNotOpen,
            isNutrientOpen = data.nutrientType.isNutrientOpen
        )
    )
}
