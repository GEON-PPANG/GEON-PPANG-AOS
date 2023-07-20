package com.sopt.geonppang.domain.model

data class SelectedFilter(
    val mainPurpose: String,
    val breadType: BreadType,
    val nutrientType: NutrientType
) {
    data class BreadType(
        val isGlutenFree: Boolean,
        val isVegan: Boolean,
        val isNutFree: Boolean,
        val isSugarFree: Boolean,
    )

    data class NutrientType(
        val isIngredientOpen: Boolean,
        val isNotOpen: Boolean,
        val isNutrientOpen: Boolean
    )
}
