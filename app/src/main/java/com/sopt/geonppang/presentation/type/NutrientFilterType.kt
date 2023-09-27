package com.sopt.geonppang.presentation.type

import androidx.annotation.StringRes
import com.sopt.geonppang.R

enum class NutrientFilterType(
    @StringRes val titleRes: Int
) {
    NUTRIENT(
        R.string.nutrient_type_nutrient_open
    ),
    INGREDIENT(
        R.string.nutrient_type_ingredient_open
    ),
    NOT(
        R.string.nutrient_type_not_open
    )
}
