package com.sopt.geonppang.presentation.type

import androidx.annotation.StringRes
import com.sopt.geonppang.R

enum class NutrientFilterType(
    val id: Int,
    @StringRes val titleRes: Int,
    @StringRes val desRes: Int
) {
    MATERIALS(
        id = 2,
        titleRes = R.string.nutrient_type_nutrient_open_title,
        desRes = R.string.nutrient_type_nutrient_open_des
    ),
    INGREDIENTS(
        id = 1,
        titleRes = R.string.nutrient_type_ingredient_open_title,
        desRes = R.string.nutrient_type_ingredient_open_des
    ),
    PRIVATE(
        id = 3,
        titleRes = R.string.nutrient_type_all_open_title,
        desRes = R.string.nutrient_type_all_open_des
    )
}
