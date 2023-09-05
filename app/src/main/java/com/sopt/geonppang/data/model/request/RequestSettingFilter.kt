package com.sopt.geonppang.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestSettingFilter(
    val mainPurpose: String,
    val breadType: BreadType,
    val nutrientType: NutrientType
) {
    @Serializable
    data class BreadType(
        val isGlutenFree: Boolean,
        val isVegan: Boolean,
        val isNutFree: Boolean,
        val isSugarFree: Boolean,
    )

    @Serializable
    data class NutrientType(
        val isNutrientOpen: Boolean,
        val isIngredientOpen: Boolean,
        val isNotOpen: Boolean,
    )
}
